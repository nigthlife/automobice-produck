<template>
  <div class="app-container mixin-components-container">
    <div class="filter-container" style="margin-bottom: 5px">
      <el-input v-model="carInfoQuery.carBrand" placeholder="汽车名称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-button class="filter-item" style="margin-left: 5px" type="primary" icon="el-icon-search" @click="handleFilter()">
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
      v-loading="carInfoLoading"
      :data="carInfoList"
      tooltip-effect="dark"
      style="width: 100%"
    >
      <el-table-column prop="carId" label="汽车编号" align="center" width="auto" />
      <el-table-column prop="clientId" label="客户编号" align="center" width="auto" />
      <el-table-column
        prop="carBrand"
        label="汽车名称"
        align="center"
        width="auto"
      />
      <el-table-column
        prop="carNumber"
        label="汽车型号"
        align="center"
        width="auto"
      />
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

    <pagination :total="total" :page.sync="carInfoQuery.page" :limit.sync="carInfoQuery.limit" @pagination="initData" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">
        <el-form-item v-if="carIdEnable" label="汽车Id">
          <el-input v-model="temp.carId" disabled />
        </el-form-item>
        <el-form-item label="客户名称" prop="clientId">
          <el-select v-model="temp.clientId" class="filter-item" placeholder="Please select">
            <el-option v-for="item in temp.carData" :key="item.clientId" :label="item.clientName" :value="item.clientId" />
          </el-select>
        </el-form-item>
        <el-form-item label="汽车名称">
          <el-input v-model="temp.carBrand" />
        </el-form-item>
        <el-form-item label="汽车品牌">
          <el-input v-model="temp.carNumber" :autosize="{ minRows: 2, maxRows: 4}" type="textarea" placeholder="Please input" />
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
import { getCarInfoAll, setCarInfo, deleteCar, updateCar } from '@/api/carInfo'
import { getSelectInfo } from '@/api/clientInfo'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

const calendarTypeOptions = [
  { key: 'CN', display_name: 'China' },
  { key: 'US', display_name: 'USA' },
  { key: 'JP', display_name: 'Japan' },
  { key: 'EU', display_name: 'Eurozone' }
]

export default {
  components: { Pagination },
  data() {
    return {
      carInfoList: [
        {
          carId: '',
          clientId: '',
          carBrand: '',
          carNumber: ''
        }
      ],
      carInfoLoading: true,
      total: 0,
      carInfoQuery: {
        page: 1,
        limit: 5,
        importance: undefined,
        userName: undefined,
        userId: undefined
      },
      dialogStatus: '',
      dialogFormVisible: false,
      carIdEnable: false,
      textMap: {
        update: '更新用户信息',
        create: '新增用户信息'
      },
      calendarTypeOptions,
      statusOptions: ['published', 'draft', 'deleted'],
      temp: {
        carId: '',
        clientId: '',
        carBrand: '',
        carNumber: '',
        carData: []
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
      this.carInfoLoading = true
      // eslint-disable-next-line no-return-assign
      getCarInfoAll(this.carInfoQuery).then(res => {
        this.carInfoList = res.items
        this.total = res.total

        setTimeout(() => {
          this.carInfoLoading = false
        }, 0.5 * 1000)
      })
    },
    handleFilter() {
      this.carInfoQuery.page = 1
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
        carId: '',
        clientId: '',
        carBrand: '',
        carNumber: '',
        carData: [],
        importance: 1
      }
    },
    handleCreate() {
      this.resetTemp()
      getSelectInfo().then(res => {
        this.temp.carData = res
      })
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.carIdEnable = false
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          setCarInfo(this.temp).then((res) => {
            this.dialogFormVisible = false
            if (res > 0) {
              this.temp.carId = res
              // unshift() 方法可向数组的开头添加一个或更多元素，并返回新的长度
              this.carInfoList.unshift(this.temp)
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
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.carIdEnable = false
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      // validate: vue中的验证器
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)

          updateCar(tempData).then((res) => {
            this.dialogFormVisible = false // 关闭编辑框
            if (res > 0) {
              const index = this.carInfoList.findIndex(v => v.carId === this.temp.carId)
              this.carInfoList.splice(index, 1, this.temp)
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
      deleteCar(row.carId).then(res => {
        if (res > 0) {
          this.$message({
            message: '删除成功！',
            type: 'success'
          })
          this.carInfoList.splice(index, 1)
        } else {
          this.$message({
            message: '删除失败哦！',
            type: 'success'
          })
        }
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
