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
      <!-- <el-table-column
        label="客户名称"
        align="center"
        width="auto"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.carInfo.clientInfo.clientName }}</span>
        </template>
      </el-table-column> -->
      <el-table-column label="创建日期" align="center" width="120">
        <template slot-scope="{row}">
          <el-popover trigger="hover" placement="top">
            <p>具体时间: {{ formatDate(row.maintainDate) }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag class="el-icon-time" size="medium">{{ row.maintainDate === null ? '未填写' : formatDate(row.maintainDate).substring(0,7) }}···</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="完成时间" align="center" width="120">
        <template slot-scope="{row}">
          <el-popover trigger="hover" placement="top">
            <p>具体时间: {{ (row.finishDate === '' || row.finishDate === null || row.finishDate === undefined ) ? '未填写': formatDate(row.finishDate) }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag class="el-icon-time" size="medium">{{ (row.finishDate == '' || row.finishDate == null || row.finishDate == undefined ) ? '未填写': formatDate(row.finishDate).substring(0,7) }}···</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        label="维修人编号"
        align="center"
        width="100"
        prop="accendantId"
      >
        <template slot-scope="{ row }">
          <el-tag :type="row.accendantId === null ? 'warning' : 'success'">
            <span>{{ row.accendantId === null ? '未填写' : row.accendantId }}</span>
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="维修状态"
        align="center"
        width="auto"
        class-name="status-col"
      >
        <template slot-scope="{row}">
          <el-tag :type="row.state === 0 ? 'warning' : 'success'">
            {{ row.state === 0 ? '维修中···': '维修完成' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="使用零件"
        align="center"
        width="auto"
      >
        <template slot-scope="{row}">
          <el-tag :type="row.usePartsId === null ? 'warning' : 'success'">
            <span>{{ row.usePartsId === null ? '未填写' : row.usePartsId }}</span>
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="收益"
        align="center"
        width="auto"
      >
        <template slot-scope="{ row }">
          <el-tag :type="row.maintainCost === null ? 'warning' : 'success'">
            <span>{{ row.maintainCost === null ? '未填写' : row.maintainCost }}</span>
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="维修信息"
        align="center"
        width="auto"
      >
        <template slot-scope="{row}">
          <el-popover trigger="hover" placement="top">
            <p>具体信息: {{ row.maintainInfo }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ row.maintainInfo === null ? '未填写' : row.maintainInfo.substring(0,2) }}···</el-tag>
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
          <el-input v-model="temp.id" disabled />
        </el-form-item>
        <el-form-item v-if="IdEnable" label="维修编号" prop="maintainNo">
          <el-input v-model="temp.maintainNo" disabled />
        </el-form-item>
        <el-form-item v-if="IdEnable" label="汽车名称" prop="carId">
          <el-input v-model="temp.carInfo.carBrand" disabled />
        </el-form-item>
        <el-form-item v-if="IdEnable" label="开始时间" prop="maintainDate">
          <el-date-picker v-model="temp.maintainDate" disabled type="datetime" placeholder="Please pick a date" />
        </el-form-item>
        <el-form-item label="完成时间" prop="finishDate">
          <el-date-picker v-model="temp.finishDate" type="datetime" placeholder="Please pick a date" />
        </el-form-item>
        <el-form-item label="维修人名" prop="accendantId">
          <el-select v-model="temp.accendantId" class="filter-item" placeholder="Please select">
            <el-option v-for="item in accendantInfo" :key="item.accendantId" :label="item.accendantName" :value="item.accendantId" />
          </el-select>
        </el-form-item>
        <el-form-item label="维修状态" prop="state">
          <el-button plain style="border:none" size="mini" @click="handleState()">
            <el-tag :type="temp.state === 0 ? 'success' : 'success'">
              {{ temp.state === 0 ? '维修中···': '维修完成' }}
            </el-tag>
          </el-button>
        </el-form-item>
        <el-form-item label="收益" prop="carId">
          <el-input v-model="temp.maintainCost" type="number" />
        </el-form-item>
        <el-form-item label="维修零件" prop="usePartsId">
          <el-select v-model="temp.usePartsId" class="filter-item" placeholder="Please select">
            <el-option v-for="item in temp.parts" :key="item.partsId" :label="item.partsName" :value="item.partsId" />
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
import { getMainAll, upMaintain } from '@/api/maintain'
import { getPartsAll } from '@/api/repertory'
import { initAccendantData } from '@/api/accendant'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

export default {
  name: 'WorkHandle',
  components: { Pagination },
  data() {
    return {
      tableData: [],
      tableDataLoading: true,
      total: 0,
      IdEnable: false,
      carInfo: [],
      accendantInfo: [],
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
        id: '',
        maintainNo: '',
        carId: '',
        maintainDate: new Date(),
        finishDate: new Date(),
        carInfo: {
          carBrand: ''
        },
        parts: [
          {
            partsInfo: {}
          }
        ],
        accendantId: '',
        state: 0,
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
      getMainAll(this.tableDataQuery).then(res => {
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
        maintainNo: '',
        carId: '',
        maintainDate: new Date(),
        finishDate: new Date(),
        accendantId: '',
        state: '',
        carInfo: {
          carBrand: ''
        },
        parts: [],
        usePartsId: '',
        maintainCost: '',
        maintainInfo: ''
      }
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // 赋值obj
      console.log(this.temp)
      this.temp.finishDate = new Date(this.temp.finishDate)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      getPartsAll().then(({ items }) => {
        this.$set(this.temp, 'parts', items)
      })
      initAccendantData().then(res => {
        this.accendantInfo = res.items
      })
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
          // tempData.finishDate = (new Date(tempData.finishDate)).getTime() / 1000
          console.log(tempData)
          upMaintain(tempData).then((res) => {
            this.dialogFormVisible = false // 关闭编辑框
            if (res > 0) {
              const index = this.tableData.findIndex(v => v.id === this.temp.id)
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
    handleState() {
      if (this.temp.state === 0) {
        this.temp.state = 1
      } else {
        this.temp.state = 0
      }
    }
  }
}
</script>

<style>

</style>
