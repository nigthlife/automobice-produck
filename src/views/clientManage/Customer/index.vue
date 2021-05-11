<template>
  <div class="app-container mixin-components-container">
    <div class="filter-container" style="margin-bottom: 5px">
      <el-input v-model="ClientInfoQuery.clientName" placeholder="客户名称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-button class="filter-item" style="margin-left: 5px" type="primary" icon="el-icon-search" @click="handleFilter">
        查询
      </el-button>
      <el-button class="filter-item" style="float:right;margin-right: 30px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        添加
      </el-button>
    </div>

    <el-table
      ref="table"
      v-loading="ClientInfoLoading"
      :data="ClientInfoList"
      tooltip-effect="dark"
      style="width: 100%"
    >
      <el-table-column prop="clientId" label="客户id" align="center" width="auto" />
      <el-table-column
        prop="clientName"
        label="客户名称"
        align="center"
        width="auto"
      />
      <el-table-column
        prop="sex"
        label="性别"
        align="center"
        width="auto"
      />
      <el-table-column
        prop="phone"
        label="电话"
        align="center"
        width="auto"
      />
      <el-table-column label="创建日期" align="center" width="170">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          <span style="margin-left: 10px">{{ formatDate(scope.row.createDate) }}</span>
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

    <pagination :total="total" :page.sync="ClientInfoQuery.page" :limit.sync="ClientInfoQuery.limit" @pagination="initData" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="100px" style="width: 450px; margin-left:70px;">
        <el-form-item v-if="clientIdEnable" label="用户Id" prop="userId">
          <el-input v-model="temp.clientId" disabled />
        </el-form-item>
        <el-form-item label="用户名称" prop="userName">
          <el-input v-model="temp.clientName" />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-input v-model="temp.sex" />
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="temp.phone" />
        </el-form-item>
        <el-form-item label="创建时间" prop="createDate">
          <el-date-picker v-model="temp.createDate" type="datetime" placeholder="Please pick a date" />
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
import { getClientInfo, addClientData, deClient, upClient } from '@/api/clientInfo'
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
      ClientInfoList: [
        {
          clientId: '',
          clientName: '',
          sex: '',
          createDate: '',
          phone: ''
        }
      ],
      ClientInfoLoading: true,
      total: 0,
      ClientInfoQuery: {
        page: 1,
        limit: 5,
        importance: undefined,
        clientName: undefined,
        clientId: undefined
      },
      dialogStatus: '',
      dialogFormVisible: false,
      clientIdEnable: false,

      textMap: {
        update: '更新用户信息',
        create: '新增用户信息'
      },
      calendarTypeOptions,
      statusOptions: ['published', 'draft', 'deleted'],
      temp: {
        clientId: '',
        clientName: '',
        sex: '',
        createDate: '',
        phone: '',
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
      this.ClientInfoLoading = true
      // eslint-disable-next-line no-return-assign
      getClientInfo(this.ClientInfoQuery).then(res => {
        this.ClientInfoList = res.items
        this.total = res.total

        setTimeout(() => {
          this.ClientInfoLoading = false
        }, 0.5 * 1000)
      })
    },
    handleFilter() {
      this.ClientInfoQuery.page = 1
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
        clientId: '',
        clientName: '',
        sex: '',
        phone: '',
        createDate: new Date(),
        importance: 1
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.clientIdEnable = false
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.temp.createDate = this.formatDate(this.temp.createDate)
          addClientData(this.temp).then((res) => {
            // console.log(res)
            this.dialogFormVisible = false
            if (res > 0) {
              this.temp.clientId = res
              // unshift() 方法可向数组的开头添加一个或更多元素，并返回新的长度
              this.ClientInfoList.unshift(this.temp)
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
      this.temp.createDate = new Date(this.temp.createDate)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.clientIdEnable = false
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      // validate: vue中的验证器
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)

          // console.log('tempData的数据为===')
          // console.log(tempData)
          tempData.createDate = +new Date(tempData.createDate)
          upClient(tempData).then((res) => {
            console.log(res)
            this.dialogFormVisible = false // 关闭编辑框
            if (res > 0) {
              const index = this.ClientInfoList.findIndex(v => v.clientId === this.temp.clientId)
              this.ClientInfoList.splice(index, 1, this.temp)
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
      deClient(row.clientId).then(res => {
        if (res > 0) {
          this.$message({
            message: '删除成功！',
            type: 'success'
          })
          this.ClientInfoList.splice(index, 1)
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
