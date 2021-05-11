<template>
  <div class="app-container mixin-components-container">
    <div class="filter-container" style="margin-bottom: 5px">
      <el-input v-model="tableDataQuery.userName" placeholder="用户名称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-button class="filter-item" type="primary" style="margin-left: 10px" icon="el-icon-search" @click="handleFilter">
        查询
      </el-button>
      <el-button class="filter-item" style="float:right;margin-right: 20px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        添加
      </el-button>
      <el-button class="filter-item" type="primary" style="float:right;margin-left: 5px" icon="el-icon-refresh" @click="initData()">
        刷新
      </el-button>
    </div>
    <el-table
      ref="table"
      v-loading="tableDataLoading"
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%"
    >
      <el-table-column prop="id" label="编号" align="center" width="50" />
      <el-table-column
        prop="maintainNo"
        label="维修编号"
        align="center"
        width="auto"
      />
      <el-table-column
        label="汽车名称"
        align="center"
        width="90"
      >
        <template slot-scope="{ row }">
          <!-- <i class="el-icon-bicycle" /> -->
          <span>{{ row.carInfo.carBrand }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="客户名称"
        align="center"
        width="auto"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.carInfo.clientInfo.clientName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建日期" align="center" width="120">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>具体时间: {{ formatDate(scope.row.maintainDate) }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag class="el-icon-time" size="medium">{{ formatDate(scope.row.maintainDate).substring(0,7) }}···</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="完成时间" align="center" width="120">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>具体时间: {{ formatDate(scope.row.finishDate) }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag class="el-icon-time" size="medium">{{ formatDate(scope.row.finishDate).substring(0,7) }}···</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        label="维修人名称"
        align="center"
        width="100"
      >
        <template slot-scope="scope">
          <!-- <i class="el-icon-bicycle" /> -->
          <span>{{ scope.row.accendant.accendantName }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="维修状态"
        align="center"
        width="auto"
        class-name="status-col"
      >
        <template slot-scope="{row}">
          <el-tag :type="row.State === 0 ? 'info' : 'success'">
            {{ row.State === 0 ? '维修中···': '维修完成' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="使用零件"
        align="center"
        width="auto"
      >
        <template slot-scope="scope">
          <!-- <i class="el-icon-position" /> -->
          <span>{{ scope.row.partsInfo.partsName }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="收益"
        align="center"
        width="auto"
      >
        <template slot-scope="{ row }">
          <span>{{ row.maintainCost === null ? '未填写' : row.maintainCost }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="维修信息"
        align="center"
        width="auto"
      >
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>具体信息: {{ scope.row.maintainInfo }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.maintainInfo.substring(0,2) }}···</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" width="100">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            编辑
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination :total="total" :page.sync="tableDataQuery.page" :limit.sync="tableDataQuery.limit" @pagination="initData" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">
        <el-form-item v-if="IdEnable" label="维修Id" prop="userId">
          <el-input v-model="temp.Id" disabled />
        </el-form-item>
        <el-form-item v-if="IdEnable" label="维修编号" prop="userName">
          <el-input v-model="temp.maintainNo" />
        </el-form-item>
        <el-form-item label="汽车名称" prop="carId">
          <el-select v-model="temp.carId" class="filter-item" placeholder="Please select">
            <el-option v-for="item in carInfo" :key="item.carId" :label="item.carNumber" :value="item.carId" />
          </el-select>
        </el-form-item>
        <el-form-item label="开始时间" prop="maintainDate">
          <el-date-picker v-model="temp.maintainDate" type="datetime" placeholder="Please pick a date" />
        </el-form-item>
        <el-form-item v-if="IdEnable" label="完成时间" prop="finishDate">
          <el-date-picker v-model="temp.finishDate" type="datetime" placeholder="Please pick a date" />
        </el-form-item>
        <el-form-item label="维修人名称" prop="accendantId">
          <el-select v-model="temp.accendantId" class="filter-item" placeholder="Please select">
            <el-option v-for="item in accendantInfo" :key="item.accendantId" :label="item.accendantName" :value="item.accendantId" />
          </el-select>
        </el-form-item>
        <el-form-item v-if="IdEnable" label="维修状态">
          <el-input v-model="temp.State" />
        </el-form-item>
        <el-form-item label="维修零件" prop="usePartsId">
          <el-select v-model="temp.usePartsId" class="filter-item" placeholder="Please select">
            <el-option v-for="item in parts.partsInfo" :key="item.partsId" :label="item.partsName" :value="item.partsId" />
          </el-select>
        </el-form-item>
        <el-form-item label="维修信息">
          <el-input v-model="temp.maintainInfo" :autosize="{ minRows: 2, maxRows: 4}" type="textarea" placeholder="Please input" />
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
import { getMaintainAll, addMaintain } from '@/api/maintain'
import { getCarInfoAll } from '@/api/carInfo'
import { getRepertoryAll } from '@/api/repertory'
import { initAccendantData } from '@/api/accendant'

import Pagination from '@/components/Pagination' // secondary package based on el-pagination

export default {
  name: 'BusinessHandle',
  components: { Pagination },
  data() {
    return {
      tableData: [],
      tableDataLoading: true,
      total: 0,
      carInfo: [],
      accendantInfo: [],
      parts: [],
      IdEnable: false,
      tableDataQuery: {
        page: 1,
        limit: 5,
        importance: undefined,
        clientName: undefined,
        carBrand: undefined
      },
      dialogStatus: '',
      dialogFormVisible: false,
      textMap: {
        update: '更新用户信息',
        create: '新增用户信息'
      },
      temp: {
        Id: '',
        maintainNo: '',
        carId: '',
        maintainDate: new Date(),
        accendantId: '',
        State: '',
        usePartsId: '',
        maintainCost: '',
        maintainInfo: ''
      },
      statusOptions: ['published', 'draft', 'deleted'],
      rules: {
        type: [{ required: true, message: 'type is required', trigger: 'change' }],
        timestamp: [{ type: 'date', required: true, message: 'timestamp is required', trigger: 'change' }],
        title: [{ required: true, message: 'title is required', trigger: 'blur' }]
      }
    }
  },
  created() {
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
      getMaintainAll(this.tableDataQuery).then(res => {
        console.log(res.items)
        this.tableData = res.items
        console.log('初始化数据为——--')
        console.log(this.tableData)
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
        Id: '',
        maintainNo: '',
        carId: '',
        maintainDate: new Date(),
        accendantId: '',
        State: '',
        usePartsId: '',
        maintainCost: '',
        maintainInfo: ''
      }
    },
    handleCreate() {
      this.resetTemp()
      getCarInfoAll().then(res => {
        this.carInfo = res.items
      })
      initAccendantData().then(res => {
        this.accendantInfo = res.items
      })
      getRepertoryAll().then(res => {
        this.partsInfo = res.items
      })
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.IdEnable = false
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          console.log(this.temp)
          // this.temp.createDate = this.formatDate(this.temp.createDate)
          addMaintain(this.temp).then((res) => {
            console.log(res)
            this.dialogFormVisible = false
            if (res > 0) {
              this.temp.userId = res
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
      this.temp.maintainDate = new Date(this.temp.maintainDate)
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

