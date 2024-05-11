<template>
  <div style="height: 100%">
    <el-container style="height: 100vh; ">
      <el-aside width="sideWidth + 'px'" style="background-color: rgb(238,241,246); height:100%" >
        <el-menu :default-openeds="['1', '3']" style="height: 100%;overflow-x: hidden"
                 background-color="rgb(30,33,81)"
                 text-color="#fff"
                 active-text-color="rgb(255,72,0)"
                 :collapse-transition="false"
                 :collapse="isCollapsed"
        >
          <div style="height: 60px;line-height: 60px; text-align: center">
            <img src="../assets/bookbar.jpg" alt="" style="width: 20px; position: relative; top: 5px;margin-right: 5px">
            <b style="color: #ffffff;" v-show="LogoTextShow">书吧管理系统</b>
          </div>
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-message"></i>
              <span slot="title">导航一</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="1-1">选项1</el-menu-item>
              <el-menu-item index="1-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="1-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="1-4">
              <template slot="title">选项4</template>
              <el-menu-item index="1-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span slot="title">导航二</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="2-1">选项1</el-menu-item>
              <el-menu-item index="2-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="2-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="2-4">
              <template slot="title">选项4</template>
              <el-menu-item index="2-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-setting"></i>
              <span slot="title">导航三</span></template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="3-1">选项1</el-menu-item>
              <el-menu-item index="3-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="3-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="3-4">
              <template slot="title">选项4</template>
              <el-menu-item index="3-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header style=" font-size: 12px;border-bottom: 1px solid #eeeeee; line-height: 60px;display: flex">
          <div style="flex: 1;font-size: 18px">
            <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
          </div>
          <el-dropdown style="width: 70px;cursor: pointer">
            <span>Crl</span>
            <i class="el-icon-arrow-down" style="margin-left: 5px"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>个人信息</el-dropdown-item>
              <el-dropdown-item>退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-header>

        <el-main>
          <template>
            <el-input
              style="padding: 10px 0;width: 110px; margin-left: 60px"
              suffix-icon="el-icon-search"
              v-model="sname">
            </el-input>
            <el-input
              style="padding: 10px 0;width: 120px; margin: 10px"
              suffix-icon="el-icon-search"
              v-model="phonenumber">
            </el-input>
            <el-input
              style="padding: 10px 0;width: 120px; margin: 10px"
              suffix-icon="el-icon-search"
              v-model="scode">
            </el-input>
            <el-button
              type="primary"
              style="margin-right: 20px"
              @click="refreshData">搜索</el-button>
            <el-button
              type="primary" style="margin-right: 20px"
              class="el-icon-circle-plus-outline"
              @click="addNewOne">新增</el-button>
            <el-button type="primary" style="margin-right: 20px" class="el-icon-remove-outline">批量删除</el-button>
            <el-table
              ref="multipleTable"
              :data="tableData"
              tooltip-effect="dark"
              style="width: 100%"
              @selection-change="handleSelectionChange"
              border stripe :header-cell-class-name="backgroundbg">
              <el-table-column
                type="selection"
                width="55">
              </el-table-column>
              <el-table-column prop="sname" label="姓名" width="120">
              </el-table-column>
              <el-table-column prop="phonenumber" label="手机号" width="140" >
              </el-table-column>
              <el-table-column prop="scode" label="账号" width="160">
              </el-table-column>
              <el-table-column prop="iaccount" label="账户金额" width="120">
              </el-table-column>
              <el-table-column prop="dcreate" label="创建日期" width="120">
              </el-table-column>
              <el-table-column prop="istage" label="会员等级" width="120">
              </el-table-column>
              <el-table-column prop="isumcount" label="累计充值" width="120">
              </el-table-column>
              <el-table-column prop="idepositcount" label="所享折扣" width="120">
              </el-table-column>
              <el-table-column prop="address" label="地址" width="120">
              </el-table-column>
              <el-table-column
                fixed="right"
                label="操作"
                width="120">
                <template slot-scope="scope">
                  <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
                  <el-button type="text" size="small">编辑</el-button>
                  <el-button type="text" size="small">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </template>

          <div class="block" style="padding: 10px 0">
            <el-pagination
              background
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="PageNumber"
              :page-sizes="[2, 5, 10, 20]"
              :page-size="PageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total">
            </el-pagination>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'HomeView',
  data () {
    return {
      tableData: [],
      total: 0,
      PageNumber: 1,
      PageSize: 10,
      sname: '',
      phonenumber: '',
      scode: '',
      isCollapsed: false,
      collapseBtnClass: 'el-icon-s-fold',
      sideWidth: 200,
      LogoTextShow: true,
      backgroundbg: 'backgroundbg',
      multipleSelection: []
    }
  },
  created () {
    this.refreshData()
  },
  methods: {
    collapse () {
      this.isCollapsed = !this.isCollapsed
      if (this.isCollapsed) {
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.LogoTextShow = false
      } else {
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.LogoTextShow = true
      }
    },
    handleClick (row) {
      console.log(row)
    },
    refreshData () {
      fetch('http://localhost:8080/consumers/getAllConsumer?' +
          'PageNumber=' + this.PageNumber +
          '&PageSize=' + this.PageSize +
          '&sname=' + this.sname +
          '&phonenumber=' + this.phonenumber +
          '&scode=' + this.scode)
        .then(res => res.json().then(res => {
          console.log(res)
          this.tableData = res.records
          this.total = res.total
        }))
    },
    handleSelectionChange () {

    },
    handleSizeChange (PageSize) {
      this.PageSize = PageSize
      this.refreshData()
    },
    handleCurrentChange (PageNumber) {
      this.PageNumber = PageNumber
      this.refreshData()
    },
    addNewOne () {
    }
  }
}
</script>
<style>
  .backgroundbg{
    background-color: #eeeeee !important;
  }
</style>
