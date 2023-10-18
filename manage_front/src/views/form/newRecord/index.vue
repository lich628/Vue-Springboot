<template>
  <div class="app-container">
    <el-form ref="form" :rules="rules" :model="form" label-width="120px">
      <el-form-item prop="name" label="姓名">
        <el-input v-model="form.name" />
      </el-form-item>

      <el-form-item prop="gender" label="性别">
        <el-select v-model="form.gender" placeholder="请选择您的性别">
          <el-option label="男" value="男" />
          <el-option label="女" value="女" />
        </el-select>
      </el-form-item>

      <el-form-item prop="phone" label="手机号">
        <el-input v-model="form.phone" />
      </el-form-item>
      <el-row>
        <el-col :sm="8" :xs="24">
          <el-form-item prop="time" label="时间">
            <el-date-picker
              v-model="form.time"
              type="datetime"
              placeholder="选择日期时间"
              align="right"
              :picker-options="pickerOptions"
              value-format="yyyy-MM-dd HH:mm:ss"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item label="物资情况" prop="isNormal">
        <el-radio :label="true" v-model="form.isNormal">正常</el-radio>
        <el-radio :label="false" v-model="form.isNormal">异常</el-radio>
      </el-form-item>

      <el-form-item label="情况描述" v-if="!form.isNormal" prop="des">
        <el-input type="textarea" v-model="form.des"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">提交记录</el-button>
        <el-button @click="onCancel">取消上传</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { insert } from "@/api/record";
export default {
  data() {
    const isName = (rule, value, callback) => {
      if (!value) {
        return new Error("请输入您的姓名");
      } else {
        const reg = /^([\u4e00-\u9fa5]{2,4}|[a-zA-Z\.\s]{1,20})$/; //2-4个中文字符正则
        if (!reg.test(value)) {
          callback(new Error("请输入2-4个中文字符姓名或英文姓名"));
        } else {
          callback();
        }
      }
    };

    const isMobileNumber = (rule, value, callback) => {
      if (!value) {
        return new Error("请输入电话号码");
      } else {
        const reg =
          /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
        const isPhone = reg.test(value);
        value = Number(value); //转换为数字
        if (typeof value === "number" && !isNaN(value)) {
          //判断是否为数字
          value = value.toString(); //转换成字符串
          if (value.length < 0 || value.length > 12 || !isPhone) {
            //判断是否为11位手机号
            callback(new Error("手机号码格式如:137xxxx1234"));
          } else {
            callback();
          }
        } else {
          callback(new Error("请输入电话号码"));
        }
      }
    };

    const isShortDes = (rule, value, callback) => {
      if (!value) {
        return new Error("请输入情况描述");
      } else {
        value = value.toString();
        if (value.length >= 200) {
          callback(new Error("字数不能超过200"));
        } else {
          callback();
        }
      }
    };

    return {
      form: {
        name: this.$store.state.user.username,
        gender: "",
        phone: "",
        state: "",
        time: "",
        desc: "",
        isNormal: "",
      },

      pickerOptions: {
        //禁用未来时间
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        //时间表的快捷选项
        shortcuts: [
          {
            text: "今天",
            onClick(picker) {
              picker.$emit("pick", new Date());
            },
          },
          {
            text: "昨天",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit("pick", date);
            },
          },
          {
            text: "一周前",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", date);
            },
          },
        ],
      },

      rules: {
        name: [
          { required: true, message: "请输入您的名字", trigger: "blur" },
          { validator: isName, trigger: "blur" },
        ],
        gender: [
          { required: true, message: "请选择您的性别", trigger: "change" },
        ],
        phone: [
          { required: true, message: "请输入手机号码", trigger: "blur" },
          { validator: isMobileNumber, trigger: "blur" },
        ],
        // tem: [
        //   { required: true, message: "请输入体温", trigger: "blur" },
        //   { validator: isTemperatrue, trigger: "blur" },
        // ],
        time: [
          {
            required: true,
            message: "请选择时间",
            trigger: "change",
          },
        ],
        isNormal: [{ required: true, message: "请选择物资情况", trigger: "blur" }],
        des: [
          { required: true, message: "请输入异常情况", trigger: blur },
          { validator: isShortDes, trigger: "blur" },
        ],
      },
    };
  },
  watch: {
    'form.isNormal': function(Val) {
      this.form.state = Val ? "正常" : "异常";
    }
  },
  methods: {
    onSubmit() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          console.log("this form will be transported-->");
          console.log(this.form);
          // 将信息传送到后端，存储到数据库
          insert(this.form).then((res) => {
            this.$message({
              message: "添加成功",
              type: "success",
            });
            this.$router.push({ path: "/query/record" }); //跳转到打卡查看表格
            this.$refs.form.resetFields();
          });
        } else {
          console.log("error submit!!");
          this.$message({
            message: "添加失败！",
            type: "error",
          });
          return false;
        }
      });
    },

    onCancel() {
      this.$message({
        message: "已取消!",
        type: "warning",
      }),
        this.$refs.form.resetFields();
    },
  },
};
</script>