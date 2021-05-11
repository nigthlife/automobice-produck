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
    </div>

    <el-table
      ref="table"
      v-loading="tableDataLoading"
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%"
    >
      <el-table-column prop="repertoryId" label="编号" align="center" width="auto" />
      <el-table-column
        label="零件信息"
        width="180"
      >
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>价格: {{ scope.row.partsInfo.price }}</p>
            <p>数量: {{ scope.row.partsInfo.count }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.partsInfo.partsName }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column min-width="100px" align="center" label="售价">
        <template slot-scope="{row}">
          <template v-if="row.edit">
            <el-input
              v-model="row.partsPrice"
              width="40px"
              class="edit-input"
              size="small"
            />
            <el-button
              class="cancel-btn"
              size="small"
              width="50px"
              icon="el-icon-refresh"
              type="warning"
              @click="cancelEdit(row)"
            >
              恢复
            </el-button>
          </template>
          <span v-else>{{ row.partsPrice }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="count"
        label="库存数量"
        align="center"
        width="auto"
      />
      <el-table-column align="center" label="操作" width="200">
        <template slot-scope="{row}">
          <el-button
            v-if="row.edit"
            type="success"
            size="small"
            icon="el-icon-circle-check-outline"
            @click="confirmEdit(row)"
          >
            Ok
          </el-button>
          <el-button
            v-else
            type="primary"
            size="small"
            icon="el-icon-edit"
            @click="row.edit=!row.edit"
          >
            编辑
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination :total="total" :page.sync="tableDataQuery.page" :limit.sync="tableDataQuery.limit" @pagination="initData" />

  </div>
</template>

<script>
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import { getRepertoryAll, upPartsPrice } from '@/api/repertory'

export default {
  name: 'RepertoryManage',
  components: { Pagination },
  data() {
    return {
      tableData: [
        {
          repertoryId: '',
          partsId: '',
          partsPrice: '',
          count: '',
          partsInfo: {
            partsId: '',
            partsName: '',
            price: '',
            count: ''
          }
        }
      ],
      tableDataLoading: true,
      total: 0,
      edit: false,
      tableDataQuery: {
        page: 1,
        limit: 5,
        importance: undefined,
        partsName: undefined,
        partsPrice: undefined
      },
      dialogStatus: '',
      dialogFormVisible: false,
      textMap: {
        update: '更新用户信息',
        create: '新增用户信息'
      },
      statusOptions: ['published', 'draft', 'deleted'],
      temp: {
        userId: undefined,
        userName: 1,
        userPwd: '',
        createDate: new Date(),
        loginName: '',
        loginState: 'published',
        importance: 1
      },
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
      getRepertoryAll(this.tableDataQuery).then(res => {
        // console.log('初始化数据为——--')
        // console.log(res.total)
        const items = res.items
        this.tableData = items.map(v => {
          this.$set(v, 'edit', false)
          v.originalTitle = v.partsPrice // 当用户点击取消按钮时将被使用
          return v
        })
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
    confirmEdit(row) {
      row.edit = false
      row.originalTitle = row.partsPrice
      upPartsPrice(row).then(res => {
        if (res > 0) {
          this.$message({
            message: '修改售价成功！',
            type: 'success'
          })
        } else {
          this.$message.error('修改售价失败哦！')
        }
      })
    },
    cancelEdit(row) {
      console.log('------cancelEdit-------')
      console.log(row)
      row.partsPrice = row.originalTitle
      row.edit = false
      this.$message({
        message: '已恢复原来的值',
        type: 'warning'
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
