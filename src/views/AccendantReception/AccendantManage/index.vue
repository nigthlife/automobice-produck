<template>
  <div class="app-container mixin-components-container">
    <div class="filter-container" style="margin-bottom: 5px">
      <el-input v-model="accendantQuery.accendantName" placeholder="维修人名称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-button class="filter-item" type="primary" style="margin-left: 5px" icon="el-icon-search" @click="handleFilter">
        查询
      </el-button>
      <el-button class="filter-item" type="primary" style="margin-left: 5px" icon="el-icon-refresh" @click="initAccendant">
        刷新
      </el-button>
      <el-button class="filter-item" style="float:right;margin-right: 30px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        添加
      </el-button>
    </div>

    <el-table
      ref="table"
      v-loading="accendantLoading"
      :data="accendantList"
      tooltip-effect="dark"
      style="width: 100%"
    >
      <el-table-column prop="accendantId" label="维修人编号" align="center" width="auto" />
      <el-table-column label="ID" prop="accendantId" sortable="custom" align="center" width="80" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span>{{ row.accendantId }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="accendantName"
        label="维修人名称"
        align="center"
        width="auto"
      />

      <el-table-column
        prop="phone"
        label="维修人电话"
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
    <pagination :total="total" :page.sync="accendantQuery.page" :limit.sync="accendantQuery.limit" @pagination="initAccendant" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">
        <el-form-item v-if="accendantIdEnable" label="维修人编号" prop="accendantId">
          <el-input v-model="temp.accendantId" disabled />
        </el-form-item>
        <el-form-item label="维修人名称" prop="accendantName">
          <el-input v-model="temp.accendantName" style="margin-left:50px;" />
        </el-form-item>
        <el-form-item label="维修人电话">
          <el-input v-model="temp.phone" style="margin-left:50px;" :autosize="{ minRows: 2, maxRows: 4}" type="textarea" placeholder="Please input" />
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
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import { initAccendantData, upAccendant, deAccendant, addAccendant } from '@/api/accendant'

export default {
  components: { Pagination },
  data() {
    return {
      accendantList: [],
      accendantLoading: true, // 表格初始化参数
      accendantQuery: {
        page: 1,
        limit: 5,
        accendantName: undefined,
        phone: undefined
      },
      total: 0, // 数据总条数
      dialogStatus: '', // 创建状态
      dialogFormVisible: false,
      accendantIdEnable: false,
      textMap: {
        update: '更新维修人员信息',
        create: '新增维修人员信息'
      },
      temp: {
        accendantId: undefined,
        accendantName: undefined,
        phone: undefined
      },
      rules: {
        type: [{ required: true, message: 'type is required', trigger: 'change' }],
        timestamp: [{ type: 'date', required: true, message: 'timestamp is required', trigger: 'change' }],
        title: [{ required: true, message: 'title is required', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.initAccendant()
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
    // table初始化函数
    initAccendant() {
      this.accendantLoading = true
      initAccendantData(this.accendantQuery).then(res => {
        this.accendantList = res.items
        this.total = res.total

        setTimeout(() => {
          this.accendantLoading = false
        }, 0.5 * 1000)
      })
    },
    // 分页触发函数
    handleFilter() {
      this.accendantQuery.page = 1
      this.initAccendant()
    },
    // 重置temp
    resetTemp() {
      this.temp = {
        accendantId: '',
        accendantName: '',
        phone: ''
      }
    },
    // 预处理添加
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.accendantIdEnable = false
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    // 添加
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          addAccendant(this.temp).then(res => {
            this.dialogFormVisible = false
            if (res) {
              this.temp.accendantId = res
              this.accendantList.unshift(this.temp)
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
    // 预处理更新
    handleUpdate(row) {
      this.temp = Object.assign({}, row)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    // 更新
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          upAccendant(tempData).then(res => {
            console.log(res)
            this.dialogFormVisible = false
            if (res > 0) {
              const index = this.accendantList.findIndex(v => v.accendantId === this.temp.accendantId)
              this.accendantList.splice(index, 1, this.temp)
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
    // 删除
    handleDelete(row, index) {
      deAccendant(row.accendantId).then(res => {
        if (res > 0) {
          this.$message({
            message: '删除成功！',
            type: 'success'
          })
          this.accendantList.splice(index, 1)
        } else {
          this.$message({
            message: '删除失败哦！',
            type: 'success'
          })
        }
      })
    },
    getSortClass: function(key) {
      const sort = this.accendantQuery.sort
      return sort === `+${key}` ? 'ascending' : 'descending'
    }
  }
}
</script>

<style>
.mixin-components-container {
  background-color: #f0f2f5;
  padding: 30px;
  min-height: calc(100vh - 84px);
}
.component-item{
  min-height: 100px;
}
</style>
