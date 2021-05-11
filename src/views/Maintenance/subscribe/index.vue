<template>
  <div class="app-container">
    <el-button class="filter-item" style="float:right;margin-right: 30px;" type="primary" icon="el-icon-edit" @click="handleCreate">
      添加
    </el-button>
    <el-table
      :data="tableData"
      style="width: 100%"
    >
      <el-table-column
        prop="id"
        label="预约编号"
        align="center"
        width="auto"
      />
      <el-table-column
        prop="clientName"
        label="用户名称"
        align="center"
        width="auto"
      >
        <template slot-scope="scope">
          <p>{{ scope.row.clientInfoList.clientName }}</p>
        </template>
      </el-table-column>
      <el-table-column
        prop="subscribeDate"
        label="创建日期"
        align="center"
        width="auto"
      >
        <template slot-scope="scope">
          <i class="el-icon-time" />
          <span style="margin-left: 10px">{{ formatDate(scope.row.subscribeDate) }}</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="phone"
        label="客户电话"
        align="center"
        width="auto"
      >
        <template slot-scope="scope">
          <p>{{ scope.row.clientInfoList.phone }}</p>
        </template>
      </el-table-column>
      <el-table-column
        prop="sex"
        label="性别"
        align="center"
        width="auto"
      >
        <template slot-scope="scope">
          <p>{{ scope.row.clientInfoList.sex }}</p>
        </template>
      </el-table-column>
      <el-table-column
        prop="subscribeInfo"
        label="预约信息"
        align="center"
        width="auto"
      >
        <template slot-scope="scope">
          <p>{{ scope.row.subscribeInfo }}</p>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        width="150px"
        align="center"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)"
          >编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="100px" style="width: 400px; margin-left:50px;">
        <el-form-item label="客户" prop="type">
          <el-input v-model="temp.type" />
        </el-form-item>
        <el-form-item label="预约时间" prop="timestamp" label-width="100px">
          <el-date-picker v-model="temp.timestamp" type="datetime" placeholder="Please pick a date" />
        </el-form-item>
        <el-form-item label="电话" prop="title">
          <el-input v-model="temp.title" />
        </el-form-item>
        <el-form-item label="Imp">
          <el-rate v-model="temp.importance" :colors="['#99A9BF', '#F7BA2A', '#FF9900']" :max="3" style="margin-top:8px;" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="temp.remark" :autosize="{ minRows: 2, maxRows: 4}" type="textarea" placeholder="Please input" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          确定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import store from '@/store/index'
import { createArticle } from '@/api/article'
// import formatDate from '@/utils/MaleWorkers'

const calendarTypeOptions = [
  { key: 'CN', display_name: 'China' },
  { key: 'US', display_name: 'USA' },
  { key: 'JP', display_name: 'Japan' },
  { key: 'EU', display_name: 'Eurozone' }
]

export default {
  data() {
    return {
      tableData: [],
      dialogStatus: '',
      dialogFormVisible: false,
      textMap: {
        update: 'Edit',
        create: 'Create'
      },
      calendarTypeOptions,
      statusOptions: ['男', '女'],
      temp: {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        type: '',
        status: '请选择'
      },
      rules: {
        type: [{ required: true, message: '客户名称不能为空', trigger: 'change' }],
        timestamp: [{ type: 'date', required: true, message: '预约时间不能为空', trigger: 'change' }],
        title: [{ required: true, message: '电话不能为空的嘞', trigger: 'blur' }]
      },
      clientId: []
    }
  },
  created() {
    this.initData()
    this.initUser()
  },
  methods: {
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
    handleEdit(index, row) {
      console.log(index, row)
    },
    handleDelete(index, row) {
      console.log(index, row)
    },
    initData() {
      // eslint-disable-next-line no-return-assign
      store.dispatch('subscribe/getSubAll').then(res => {
        console.log(res)
        this.tableData = res.data
      })
    },
    initUser() {
      store.dispatch('user/getUId').then(res => {
        console.log('获取的用户id')
        console.log(res)
        this.clientId = res.userId
      })
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        status: '请选择',
        type: ''
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          // 设置temp 的id
          this.temp.id = 1
          this.temp.author = 'vue-element-admin'
          console.log(this.temp)
          createArticle(this.temp).then(() => {
            this.list.unshift(this.temp)
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: 'Created Successfully',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    }
  }

}
</script>

<style>

</style>
