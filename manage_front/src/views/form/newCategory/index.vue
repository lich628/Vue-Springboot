<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-form-item prop="name" label="物资名称">
        <el-input v-model="form.name" />
      </el-form-item>

      <el-form-item prop="num" label="物资数量">
        <el-input v-model="form.num" />
      </el-form-item>

      <el-form-item prop="flag" label="是否重点物资">
        <el-select v-model="form.flag" placeholder="请选择是或否">
          <el-option label="是" value="是" />
          <el-option label="否" value="否" />
        </el-select>
      </el-form-item>

      <el-form-item prop="phone" label="负责人电话">
        <el-input v-model="form.phone" />
      </el-form-item>

      <el-form-item prop="file" label="物资图片">
        <!-- 上传图片组件 action为接收上传的后端地址 -->
        <el-upload
          v-if="isShowUpload"
          ref="upload"
          class="avatar-uploader"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :headers="headers"
          :limit="1"
          :on-exceed="handleExceed"
          :auto-upload="false"
          :on-change="onChange"
          accept=".jpg,.png,.jpeg"
          name="file"
        >
          <i class="el-icon-plus avatar-uploader-icon" />
        </el-upload>
      </el-form-item>

      <el-row v-if="isShowImgUpload">
        <el-col :span="6" style="color: white">1</el-col>
        <el-col :span="10">
          <div style="position: relative; background-color: #f0f3fa">
            <el-image
              :src="localUrl"
              :preview-src-list="[localUrl]"
              style="width: 100%; height: 100%"
              fit="scale-down"
            ></el-image>
          </div>
          <div style="padding: 10% 40%">
            <el-button
              type="primary"
              :loading="false"
              size="small"
              @click="reLoad"
              >重新上传</el-button
            >
          </div>
        </el-col>
        <el-col :span="4" style="color: white">1</el-col>
      </el-row>

      <el-form-item>
        <el-button type="primary" @click="onSubmit">提交记录</el-button>
        <el-button @click="onCancel">取消上传</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { insert } from "@/api/category";

export default {
  data() {
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
            callback(new Error("手机号码格式如:138xxxx8754"));
          } else {
            callback();
          }
        } else {
          callback(new Error("请输入电话号码"));
        }
      }
    };

    const isTrueNumber = (rule, value, callback) => {
      if (!value) {
        return new Error("请输入数量");
      } else {
        value = Number(value); //转换为数字
        if (typeof value === "number" && !isNaN(value)) {
          //判断是否为数字
          if (value <= 0) {
            callback(new Error("数量应该大于0"));
          } else {
            callback();
          }
        } else {
          callback(new Error("请正确输入数量"));
        }
      }
    };

    return {
      isShowImgUpload: false,
      isShowUpload: true,

      form: {
        name: "",
        num: "",
        flag: "",
        phone: "",
        file: "",
      },

      rules: {
        name: [{ required: true, message: "请输入物品名称", trigger: "blur" }],

        num: [
          { required: true, message: "请输入数量", trigger: "blur" },
          { validator: isTrueNumber, trigger: "blur" },
        ],

        flag: [{ required: true, message: "请选择", trigger: "blur" }],

        phone: [
          { required: true, message: "请输入手机号码", trigger: "blur" },
          { validator: isMobileNumber, trigger: "blur" },
        ],

        file: [{ required: true, message: "请上传一张图片", trigger: "blur" }],
      },
    }; //end of return
  }, //end of data()

  methods: {
    clearForm() {
      //多处使用清除表格操作
      this.isShowImgUpload = false;
      this.isShowUpload = true;
      this.$refs.form.resetFields();
    },

    onSubmit() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          console.log("onSubmit this form-->");
          console.log(this.form);

          //必须新建formData来才能把图片传到后端
          let formData = new FormData();
          for (let key in this.form) {
            formData.append(key, this.form[key]);
            console.log(this.form[key] + " --> " + formData[key]);
          }

          insert(formData).then((res) => {
            this.$message({
              message: "添加成功",
              type: "success",
            });
            this.$router.push({ path: "/query/category" }); //跳转到物资查看表格
            this.clearForm();
          });
        }
      });
    },

    onCancel() {
      this.$message({
        message: "已取消!",
        type: "warning",
      }),
        this.clearForm();
    },

    //重新选择图片
    reLoad() {
      this.isShowImgUpload = false; //呈现本地预览组件
      this.isShowUpload = true; //隐藏上传组件
      this.form.file = ""; //清除表单中的文件记录
      this.$refs.upload.clearFiles(); //不符合的文件类型，清除已经上传的文件
    },

    handleExceed() {
      this.$message({
        type: "error",
        message: "只能上传1张图片！",
        duration: 1000 * 3,
      });
    },

    //可用于预览图片
    onChange(event) {
      const isPic =
        ["image/jpeg", "image/png", "image/jpg"].indexOf(event.raw.type) != -1;
      const isLt = event.raw.size / 1024 / 1024 < 5;
      if (!isPic) {
        this.$message({
          type: "error",
          message: "上传的图片只能是 png 或 jpg 格式!",
          duration: 1000 * 3,
        });
      }
      if (!isLt) {
        this.$message({
          type: "error",
          message: "上传图片大小不能超过 5MB!",
          duration: 1000 * 2,
        });
      }
      if (!isPic || !isLt) {
        this.form.file = ""; //清除表单文件记录
        this.$refs.upload.clearFiles(); //不符合的文件类型，清除已经上传的文件
        return;
      }

      //event.raw为图片实体，可通过consolse打印查看详情
      // 获取上传图片的本地URL，用于上传前的本地预览
      var URL = null;
      if (window.createObjectURL != undefined) {
        // basic
        URL = window.createObjectURL(event.raw);
      } else if (window.URL != undefined) {
        // mozilla(firefox)
        URL = window.URL.createObjectURL(event.raw);
      } else if (window.webkitURL != undefined) {
        // webkit or chrome
        URL = window.webkitURL.createObjectURL(event.raw);
      }
      // 转换后的地址为 blob:http://xxx/7bf54338-74bb-47b9-9a7f-7a7093c716b5
      this.localUrl = URL;
      this.isShowImgUpload = true; //呈现本地预览组件
      this.isShowUpload = false; //隐藏上传组件
      this.form.file = event.raw;
      console.log("the file of form is-->");
      console.log(this.form.file);
    },

    //图片成功上传，此法实际无用
    handleAvatarSuccess(response, file, fileList) {
      console.log("从后端返回的response-->");
      console.log(response);
      if (response && response.data && response.data.url) {
        this.$message({
          type: "success",
          message: "上传图像成功",
          duration: 1000 * 2,
        });
      } else {
        this.$message.error("上传图像失败！");
      }
    },

    // 图片上传之前调用的方法，此法实际无用
    beforeAvatarUpload(file) {
      const isPic = ["image/jpeg", "image/png"].indexOf(file.type) != -1;
      const isLt = file.size / 1024 / 1024 < 5;
      if (!isPic) {
        this.$message({
          type: "error",
          message: "上传的图片只能是 png 或 jpg 格式!",
          duration: 1000 * 3,
        });
      }
      if (!isLt) {
        this.$message({
          type: "error",
          message: "上传图片大小不能超过 5MB!",
          duration: 1000 * 2,
        });
      }
      return isPic && isLt;
    },
  },
};
</script>

<style lang='scss'>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>