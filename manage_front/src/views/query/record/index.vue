<template>
  <div class="app-container">
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      height="calc(100vh - 100px)"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="序号" min-width="80">
        <template slot-scope="scope">
          {{ scope.row.id }}
        </template>
      </el-table-column>

      <el-table-column
        label="姓名"
        sortable
        prop="name"
        align="center"
        min-width="100"
      >
        <template slot-scope="scope">
          {{ scope.row.name }}
        </template>
      </el-table-column>

      <el-table-column label="性别" min-width="100" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.gender }}</span>
        </template>
      </el-table-column>

      <el-table-column label="手机号" min-width="170" align="center">
        <template slot-scope="scope">
          {{ scope.row.phone }}
        </template>
      </el-table-column>

      <el-table-column
        class-name="status-col"
        sortable
        prop="state"
        label="仓库运行情况"
        min-width="120"
        align="center"
      >
        <template slot-scope="scope">
          <el-tag
            :type="
              scope.row.state=='异常'
                ? 'warning'
                : 'success'
            "
            >{{ scope.row.state}}
            </el-tag>
        </template>
      </el-table-column>

      <el-table-column
        align="center"
        sortable
        prop="time"
        label="打卡时间"
        min-width="200"
      >
        <template slot-scope="scope">
          <i class="el-icon-time" />
          <span style="margin-left: 5px">{{ scope.row.time }}</span>
        </template>
      </el-table-column>

      <el-table-column label="情况描述" min-width="200" align="center">
        <template slot-scope="scope">
          <div style="max-height: 90px; overflow-y: auto">
            {{ scope.row.des }}
          </div>
        </template>
      </el-table-column>

      <el-table-column fixed="right" label="操作" width="100" align="center">
        <template slot-scope="scope">
          <el-button
            @click.native.prevent="deleteRow(scope.row.id, list)"
            type="danger"
            size="small"
            plain
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { getList, deleteTuple } from "@/api/record";

export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: "success",
        draft: "gray",
        deleted: "danger",
      };
      return statusMap[status];
    },
  },
  data() {
    return {
      list: null,
      listLoading: true,
    };
  },
  created() {
    this.fetchData();
  },
  methods: {
    fetchData() {
      this.listLoading = true;
      getList().then((response) => {
        this.list = response.data.items;
        this.listLoading = false;
      });
    },

    async deleteRow(id, list) {
    this.listLoading = true; // 设置加载状态
    console.log(id);
    const tempid = list.find((item) => item.id === id).id;
    console.log(tempid);
    const index = list.findIndex((item) => item.id === tempid);
    if (index !== -1) {
      list.splice(index, 1);
      try {
        await deleteTuple(tempid);
        this.$message({
          message: "删除成功",
          type: "success",
        });
      } catch (err) {
        this.$message({
          message: "错误：前端数据被删除，但后端数据库数据并未更新！",
          type: "warning",
        });
      }
    } else {
      console.error(`未找到ID为 ${id} 的行`);
    }

    this.listLoading = false; // 操作完成后设置加载状态为 false
  },
  },
};
</script>
