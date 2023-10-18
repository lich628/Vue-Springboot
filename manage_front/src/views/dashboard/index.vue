<template>
  <div class="dashboard-container">
    <div class="dashboard-text">
      <h1>欢迎来到应急物资管理系统</h1>
      <h2>你好，{{ username }}</h2>
    </div>
    <div class="pic-container">
      <div class="gif-container">
        <img src="../../assets/dashbord/walkingman.gif" alt="" />
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "Dashboard",
  computed: {
    ...mapGetters(["username"]),
  },
};
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    background-color: #f0f0f0;
    height: 85vh;
  }
  &-text {
    text-align: center;
    font-family: 'Arial', sans-serif;
    color: #333;
  }
  &-text h1 {
    font-size: 36px;
    margin-bottom: 10px;
  }
  &-text h2 {
    font-size: 24px;
    font-weight: bold;
  }
  &-text h1,
  &-text h2 {
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
  }
  &-text h1::after,
  &-text h2::after {
    content: "";
    display: block;
    width: 100px;
    height: 2px;
    background-color: #007bff; /* 添加下划线效果 */
    margin: 10px auto;
  }
  .pic-container {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 20px;
  }
  .gif-container {
    animation: bounce 2s infinite;
    background-color: transparent;
  }
}

@keyframes bounce {
  0%, 20%, 50%, 80%, 100% {
    transform: translateY(0);
  }
  40% {
    transform: translateY(-20px);
  }
  60% {
    transform: translateY(-10px);
  }
}
</style>
