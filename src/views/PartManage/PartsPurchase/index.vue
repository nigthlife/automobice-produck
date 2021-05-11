<template>
  <div class="app-container mixin-components-container">
    <div class="filter-container" style="margin-bottom: 5px">
      <el-input v-model="tableDataQuery.partsName" placeholder="零件名称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-button class="filter-item" type="primary" style="margin-left: 10px" icon="el-icon-search" @click="handleFilter">
        查询
      </el-button>
      <el-button class="filter-item" type="primary" style="margin-left: 5px" icon="el-icon-refresh" @click="initData()">
        刷新
      </el-button>
      <el-button class="filter-item" style="float:right;margin-right: 13px;" type="primary" icon="el-icon-edit" @click="handleCreate">
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
      <el-table-column prop="partsId" label="零件id" align="center" width="auto" />
      <el-table-column
        prop="partsName"
        label="零件名称"
        align="center"
        width="auto"
      />
      <el-table-column
        prop="price"
        label="价格"
        align="center"
        width="auto"
      />
      <el-table-column
        prop="count"
        label="数量"
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
      <el-table-column align="center" label="购买" width="130">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" icon="el-icon-shopping-cart-full" @click="handlePurchase(row)">
            购买
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination :total="total" :page.sync="tableDataQuery.page" :limit.sync="tableDataQuery.limit" @pagination="initData" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">
        <el-form-item v-if="partsIdEnable" label="零件Id" prop="partsId">
          <el-input v-model="temp.partsId" disabled />
        </el-form-item>
        <el-form-item v-if="Edit" label="零件名称" prop="partsName">
          <el-input v-model="temp.partsName" />
        </el-form-item>
        <el-form-item v-if="Edit" label="价格" prop="price">
          <el-input v-model="temp.price" type="number" />
        </el-form-item>
        <el-form-item v-if="Edit" label="数量" prop="count">
          <el-input v-model="temp.count" type="number" />
        </el-form-item>
        <el-form-item v-if="purchaseEnable" label="购买数量" prop="purchaseNum">
          <el-input v-model="temp.purchaseNum" type="number" />
        </el-form-item>
        <el-form-item label="Imp">
          <el-rate v-model="temp.importance" :colors="['#99A9BF', '#F7BA2A', '#FF9900']" :max="3" style="margin-top:8px;" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button v-if="Edit" type="primary" @click="dialogStatus==='create'?createData():updateData()">
          确认
        </el-button>
        <el-button v-if="purchaseEnable" type="primary" @click="PurchaseData()">
          确认
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import { getPartsInfo, addParts, dePartsId, upParts } from '@/api/partsInfo'
import { purchaseParts } from '@/api/repertory'

export default {
  name: 'PartsPurchase',
  components: { Pagination },
  data() {
    return {
      // 数据
      tableData: [
        {
          partsId: '',
          partsName: '',
          price: '',
          count: ''
        }
      ],
      // 加载table参数
      tableDataLoading: true,
      partsIdEnable: false,
      purchaseEnable: false,
      Edit: true,
      // 数据总条数
      total: 0,
      purchaseNum: 1,
      // 分页查询对象，可根据名称和编号模糊查询
      tableDataQuery: {
        page: 1,
        limit: 5,
        importance: undefined,
        partsName: undefined,
        partsId: undefined
      },
      // 模态框状态
      dialogStatus: '',
      // 模态框状态
      dialogFormVisible: false,
      textMap: {
        update: '更新零件信息',
        create: '新增零件信息',
        purchase: '购买零件'
      },
      // 临时对象
      temp: {
        partsId: '',
        partsName: '',
        price: '',
        count: '',
        purchaseNum: 1,
        importance: 1
      },
      // 提示信息
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
      getPartsInfo(this.tableDataQuery).then(res => {
        console.log('初始化数据为——--')
        console.log(res.total)
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
    resetTemp() {
      this.temp = {
        partsId: '',
        partsName: '',
        price: '',
        count: '',
        purchaseNum: 1,
        importance: 1
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.partsIdEnable = false
      this.Edit = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          addParts(this.temp).then((res) => {
            console.log(res)
            this.dialogFormVisible = false
            if (res > 0) {
              this.temp.partsId = res
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
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.Edit = true
      this.partsIdEnable = false
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      // validate: vue中的验证器
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          upParts(tempData).then((res) => {
            console.log(res)
            this.dialogFormVisible = false // 关闭编辑框
            if (res > 0) {
              const index = this.tableData.findIndex(v => v.partsId === this.temp.partsId)
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
      dePartsId(row.partsId).then(res => {
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
    handlePurchase(row) {
      this.resetTemp()
      this.temp = Object.assign({}, row) // 赋值obj
      this.dialogStatus = 'purchase'
      this.dialogFormVisible = true
      this.Edit = false
      this.partsIdEnable = false
      this.purchaseEnable = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    PurchaseData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          purchaseParts(tempData).then(res => {
            this.dialogFormVisible = false // 关闭编辑框
            if (res > 0) {
              this.$message({
                message: '购买成功！',
                type: 'success'
              })
            } else {
              this.$message.error('购买失败哦！')
            }
          })
        }
      })
    },
    handleChange(value) {
      console.log(value)
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
.edit-input {
  padding-right: 100px;
}
.cancel-btn {
  position: absolute;
  right: 15px;
  top: 10px;
}
</style>
