<template>
  <div class="app-container mixin-components-container">
    <div class="filter-container" style="margin-bottom: 5px">
      <el-input v-model="tableDataQuery.cilentId" placeholder="客户Id" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-button class="filter-item" style="margin-left: 10px" type="primary" icon="el-icon-search" @click="handleFilter">
        查询
      </el-button>
      <el-button class="filter-item" type="primary" style="margin-left: 5px" icon="el-icon-refresh" @click="initData()">
        刷新
      </el-button>
      <el-button class="filter-item" style="float:right;margin-right: 30px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        添加
      </el-button>
    </div>

    <el-table
      ref="table"
      v-loading="tableDataLoading"
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%"
    >
      <el-table-column prop="id" label="预约编号" align="center" width="auto" />
      <el-table-column
        label="客户名称"
        align="center"
        width="auto"
      >
        <template slot-scope="scope" class="component-item">
          <el-popover trigger="hover" placement="top">
            <p align="center">具体信息</p>
            <p>性别: {{ scope.row.clientInfoList.sex }}</p>
            <p>电话: {{ scope.row.clientInfoList.phone }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium"><i class="el-icon-user-solid" style="margin-right: 5px" />{{ scope.row.clientInfoList.clientName }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        label="汽车名称"
        align="center"
        width="auto"
      >
        <template slot-scope="scope" class="component-item">
          <div slot="reference" class="name-wrapper">
            <el-tag size="medium">{{ scope.row.carInfo.carBrand }}</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="subscribeInfo"
        label="预约信息"
        align="center"
        width="auto"
      />
      <el-table-column label="预约日期" align="center" width="170">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          <span style="margin-left: 10px">{{ formatDate(scope.row.subscribeDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" width="200">
        <template slot-scope="{row,$index}">
          <el-button
            v-if="row.edit"
            type="success"
            size="small"
            icon="el-icon-circle-check-outline"
            @click="confirmEdit(row)"
          >
            Ok
          </el-button>
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            编辑
          </el-button>
          <el-button v-if="row.status!='deleted'" size="mini" type="danger" @click="handleDelete(row,$index)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination :total="total" :page.sync="tableDataQuery.page" :limit.sync="tableDataQuery.limit" @pagination="initData" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">
        <el-form-item v-if="false" label="预约编号" prop="id">
          <el-input v-model="temp.id" disabled />
        </el-form-item>
        <el-form-item v-if="IdEnable" label="客户名称" prop="cilentId">
          <el-select v-model="temp.cilentId" class="filter-item" placeholder="Please select" @change="handleGetCarSelect">
            <el-option v-for="item in temp.clientData" :key="item.clientId" :label="item.clientName" :value="item.clientId" />
          </el-select>
        </el-form-item>
        <el-form-item v-if="carInfoDataEnable" label="汽车名称" prop="carId">
          <el-select v-model="temp.carId" class="filter-item" placeholder="Please select" @change="handleGetCarSelect">
            <el-option v-for="item in temp.carInfoData" :key="item.carId" :label="item.carBrand" :value="item.carId" />
          </el-select>
        </el-form-item>
        <el-form-item label="预约时间" prop="subscribeDate">
          <el-date-picker v-model="temp.subscribeDate" type="datetime" placeholder="Please pick a date" />
        </el-form-item>
        <el-form-item label="预约信息">
          <el-input v-model="temp.subscribeInfo" :autosize="{ minRows: 2, maxRows: 4}" type="textarea" placeholder="Please input" />
        </el-form-item>
        <el-form-item label="Imp">
          <el-rate v-model="temp.importance" :colors="['#99A9BF', '#F7BA2A', '#FF9900']" :max="3" style="margin-top:8px;" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          确认
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getSubscribeAll, upSubscribe, InSubscribe, DelSubscribe } from '@/api/subscribe'
import { getSelectInfo } from '@/api/clientInfo'
import { getClientCar } from '@/api/carInfo'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

const calendarTypeOptions = [
  { key: 'CN', display_name: 'China' },
  { key: 'US', display_name: 'USA' },
  { key: 'JP', display_name: 'Japan' },
  { key: 'EU', display_name: 'Eurozone' }
]

export default {
  components: {
    Pagination
  },
  data() {
    return {
      tableData: [
        {
          id: '',
          clientInfoList: {
            clientName: '',
            phone: '',
            createDate: '',
            sex: ''
          },
          carInfo: {
            carBrand: ''
          },
          subscribeDate: '',
          subscribeInfo: ''
        }
      ],
      tableDataLoading: true,
      carInfoData: [],
      total: 0,
      tableDataQuery: {
        page: 1,
        limit: 5,
        importance: undefined,
        subscribeInfo: undefined
      },
      dialogStatus: '',
      dialogFormVisible: false,
      IdEnable: false,
      carInfoDataEnable: false,
      textMap: {
        update: '更新预约信息',
        create: '新增预约信息'
      },
      calendarTypeOptions,
      statusOptions: ['published', 'draft', 'deleted'],
      temp: {
        id: '',
        cilentId: '',
        carId: '',
        clientData: [],
        carInfoData: [],
        clientInfoList: {
          clientName: '',
          phone: '',
          createDate: '',
          sex: ''
        },
        subscribeDate: new Date(),
        subscribeInfo: '',
        importance: 1
      },
      rules: {
        type: [{ required: true, message: 'type is required', trigger: 'change' }],
        timestamp: [{ type: 'date', required: true, message: 'timestamp is required', trigger: 'change' }],
        title: [{ required: true, message: 'title is required', trigger: 'blur' }]
      }
    }
  },
  created: function() {
    this.initData()
  },
  methods: {
    handleEdit(index, row) {
      console.log(index, row)
    },
    calHeight() {
      this.$nextTick(() => {
        const rect = this.$refs.container.getBoundingClientRect()
        this.tableHeight = rect.height
      })
    },
    initData() {
      this.tableDataLoading = true
      // eslint-disable-next-line no-return-assign
      getSubscribeAll(this.tableDataQuery).then(res => {
        this.tableData = res.items
        this.total = res.total

        setTimeout(() => {
          this.tableDataLoading = false
        }, 0.5 * 1000)
      })
    },
    handleFilter() {
      this.tableDataQuery.page = 1
      this.initData()
    },
    formatDate(now) {
      var date = new Date(now)
      var year = date.getFullYear() // 取得4位数的年份
      var month = date.getMonth() + 1 // 取得日期中的月份，其中0表示1月，11表示12月
      var dat = date.getDate() // 返回日期月份中的天数（1到31）
      var hour = date.getHours() // 返回日期中的小时数（0到23）
      var minute = date.getMinutes() // 返回日期中的分钟数（0到59）
      var second = date.getSeconds() // 返回日期中的秒数（0到59）
      return year + '-' + month + '-' + dat + ' ' + hour + ':' + minute + ':' + second
    },
    resetTemp() {
      this.temp = {
        id: '',
        cilentId: '',
        clientData: [],
        clientInfoList: {
          clientName: '',
          phone: '',
          createDate: '',
          sex: ''
        },
        subscribeDate: new Date(),
        subscribeInfo: '',
        importance: 1
      }
    },
    getSelectData() {
      getSelectInfo().then(res => {
        console.log(res)
        this.temp.clientData = res
      })
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.IdEnable = true
      this.$nextTick(() => {
        this.getSelectData()
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.temp.subscribeDate = this.formatDate(this.temp.subscribeDate)
          console.log('======选择的客户名称为=====')
          const name = this.temp.clientData[this.temp.cilentId - 1].clientName
          InSubscribe(this.temp).then((res) => {
            this.dialogFormVisible = false
            if (res > 0) {
              this.temp.id = res
              this.temp.clientInfoList.clientName = name
              // unshift() 方法可向数组的开头添加一个或更多元素，并返回新的长度
              this.tableData.unshift(this.temp)
              this.$message({
                message: '新增成功！',
                type: 'success'
              })
            } else {
              this.$message.error('新增失败哦！')
            }
          })
        }
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // 赋值obj
      this.temp.subscribeDate = new Date(this.temp.subscribeDate)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.IdEnable = false
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      // validate: vue中的验证器
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)

          tempData.subscribeDate = +new Date(tempData.subscribeDate)
          const name = this.temp.clientData[this.temp.cilentId - 1].clientName
          upSubscribe(tempData).then((res) => {
            this.dialogFormVisible = false // 关闭编辑框
            if (res > 0) {
              this.temp.clientInfoList.clientName = name
              const index = this.tableData.findIndex(v => v.Id === this.temp.Id)
              this.tableData.splice(index, 1, this.temp)
              this.$message({
                message: '修改成功！',
                type: 'success'
              })
            } else {
              this.$message.error('修改失败哦！')
            }
          })
        }
      })
    },
    handleDelete(row, index) {
      DelSubscribe(row.id).then(res => {
        if (res > 0) {
          this.$message({
            message: '删除成功！',
            type: 'success'
          })
          this.tableData.splice(index, 1)
        } else {
          this.$message({
            message: '删除失败哦！',
            type: 'success'
          })
        }
      })
    },
    handleGetCarSelect() {
      console.log('-------handleGetCarSelect---------')
      console.log(this.temp.cilentId)

      getClientCar(this.temp.cilentId).then(res => {
        // this.carInfoData = res
        this.$set(this.temp, 'carInfoData', res)
        this.carInfoDataEnable = true
      })
    }
  }
}
</script>

<style>
.header {
  flex-shrink: 0;
}

.container {
  display: flex;
  flex-direction: column;
}
.mixin-components-container {
  background-color: #f0f2f5;
  padding: 30px;
  min-height: calc(100vh - 84px);
}
.component-item{
  min-height: 100px;
}
</style>
