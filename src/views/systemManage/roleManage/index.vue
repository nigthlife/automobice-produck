<template>
  <div class="app-container mixin-components-container">
    <div class="filter-container" style="margin-bottom: 5px">
      <el-input v-model="tableDataQuery.roleName" placeholder="用户名称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-button class="filter-item" type="primary" style="margin-left: 10px" icon="el-icon-search" @click="handleFilter">
        查询
      </el-button>
      <el-button class="filter-item" type="primary" style="margin-left: 5px" icon="el-icon-refresh" @click="initData()">
        刷新
      </el-button>
      <el-button class="filter-item" style="float:right;margin-right: 30px;" type="primary" icon="el-icon-edit" @click="handleCreate()">
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
      <el-table-column prop="roleId" label="角色id" align="center" width="auto" />
      <el-table-column
        prop="roleName"
        label="角色名称"
        align="center"
        width="auto"
      />
      <el-table-column
        prop="roleDesc"
        label="角色信息"
        align="center"
        width="auto"
      />
      <el-table-column
        label="登录状态"
        align="center"
        width="auto"
        class-name="status-col"
      >
        <template slot-scope="{row}">
          <el-tag :type="row.isEnable === 0 ? 'info' : 'success'">
            {{ row.isEnable === 1 ? '可用' : '不可用' }}
          </el-tag>
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
        <el-form-item v-if="IdEnable" label="角色Id" prop="userId">
          <el-input v-model="temp.userId" disabled />
        </el-form-item>
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="temp.roleName" />
        </el-form-item>
        <el-form-item label="角色信息" prop="roleDesc">
          <el-input v-model="temp.roleDesc" />
        </el-form-item>
        <el-form-item v-if="IdEnable" label="角色状态">
          <el-button plain style="border:none" size="mini" @click="handleState()">
            <el-tag :type="temp.isEnable === 0 ? 'success' : 'success'">
              {{ temp.isEnable === 0 ? '不可用': '可用' }}
            </el-tag>
          </el-button>
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
import { getClientInfo } from '@/api/role'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

export default {
  name: 'Role',
  components: { Pagination },
  data() {
    return {
      tableData: [],
      tableDataLoading: true,
      total: 0,
      tableDataQuery: {
        page: 1,
        limit: 5,
        importance: undefined,
        roleName: undefined,
        roleId: undefined
      },
      dialogStatus: '',
      dialogFormVisible: false,
      IdEnable: false,
      textMap: {
        update: '更新用户信息',
        create: '新增用户信息'
      },
      statusOptions: ['published', 'draft', 'deleted'],
      temp: {
        roleId: '',
        roleName: '',
        roleDesc: '',
        isEnable: '',
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
      getClientInfo(this.tableDataQuery).then(res => {
        this.tableData = res.items
        this.total = res.total

        setTimeout(() => {
          this.tableDataLoading = false
        }, 0.5 * 1000)
      })
    },
    resetTemp() {
      this.temp = {
        roleId: '',
        roleName: '',
        roleDesc: '',
        isEnable: '',
        importance: 1
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
          this.dialogFormVisible = false
          const number = this.tableData
          var roleIdNumber = 0
          number.map(its => {
            if (roleIdNumber < its.roleId) {
              roleIdNumber = its.roleId
            }
            return null
          })
          console.log(roleIdNumber)
          this.temp.roleId = roleIdNumber + 1
          this.temp.isEnable = 0
          // unshift() 方法可向数组的开头添加一个或更多元素，并返回新的长度
          this.tableData.unshift(this.temp)
          this.$message({
            message: '新增成功！',
            type: 'success'
          })
        }
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // 赋值obj
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      // validate: vue中的验证器
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          // const tempData = Object.assign({}, this.temp)
          this.dialogFormVisible = false // 关闭编辑框
          const index = this.tableData.findIndex(v => v.roleId === this.temp.roleId)
          this.tableData.splice(index, 1, this.temp)
          this.$message({
            message: '修改成功！',
            type: 'success'
          })
        }
      })
    },
    handleDelete(row, index) {
      this.$message({
        message: '不可删除！',
        type: 'error'
      })
      // this.tableData.splice(index, 1)
    },
    handleState() {
      if (this.temp.isEnable === 0) {
        this.temp.isEnable = 1
      } else {
        this.temp.isEnable = 0
      }
    },
    handleFilter() {
      this.tableDataQuery.page = 1
      this.initData()
    }
  }
}
</script>

<style>
</style>
