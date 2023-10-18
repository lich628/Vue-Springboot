<template>
  <div id="test_app">
    <!--echarts的容器-->
    <div id="main" style="width: 90%; height: 70vh"></div>
  </div>
</template>

   
<script>
import * as echarts from "echarts";
import {mockData} from "./mock/index.js"
export default {
  name: "",
  data() {
    return {
      charts: "",
      opinionData1: mockData.map(item=>item.temperature), //温度数据
      opinionData2: mockData.map(item=>item.humidity), //湿度数据
      xAxisData:[]
    };
  },
  methods: {
    drawLine(id) {
      var myChart = echarts.init(document.getElementById(id));
      myChart.setOption({
        title: {
          left: "3%",
          top: "5%",
          text: "物资环境监控", //标题文本，支持使用 \n 换行
          subtext: "数据来自仓储中心",
        },
        tooltip: {
          trigger: "axis",
        },
        legend: {
          align: "right", //文字在前图标在后
          left: "13%",
          top: "15%",
          data: ["", "", ""],
        },
        color: ["rgb(255,96,64)", "rgb(230, 197, 91)", "rgb(0, 0, 0)"],
        grid: {
          top: "30%",
          left: "5%",
          right: "5%",
          bottom: "5%",
          containLabel: true,
        },

        toolbox: {
          feature: {
            saveAsImage: {}, //保存为图片
          },
        },
        xAxis: {
          type: "category",
          boundaryGap: true,
          axisTick: {
            alignWithLabel: true, //保证刻度线和标签对齐
          },
          data: this.xAxisData //x坐标的名称
        },
        yAxis: {
          type: "value",
          boundaryGap: true,
          splitNumber: 6, //纵坐标数
          //interval: 30, //强制设置坐标轴分割间隔
        },

        series: [
          {
            name: "温度°C",
            type: "line", //折线图line;柱形图bar;饼图pie
            stack: "温度",
            itemStyle: {
              color: "rgb(255,96,64)", //改变折线点的颜色
              lineStyle: {
                color: "rgb(255,96,64)", //改变折线颜色
              },
            },
            data: this.opinionData1,
          },
          {
            name: "湿度 %",
            type: "line", //折线图line;柱形图bar;饼图pie
            stack: "湿度",
            itemStyle: {
              color: "rgb(230, 197, 91)", //改变折线点的颜色
              lineStyle: {
                color: "rgb(230, 197, 91)", //改变折线颜色
              },
            },
            data: this.opinionData2,
          },
        ],
      });
      /*窗口尺寸发生变化时，echarts 实例重置尺寸*/
      window.addEventListener("resize", function () {
        myChart.resize();
      });
    },

    // 生成 x 轴时间数据（假设获取最近 6 个小时的时间戳）
    generateXAxisData() {
  const now = new Date();
  const xAxisData = [];

  for (let i = 5; i >= 0; i--) {
    const hour = now.getHours() - i; // 获取当前小时数并减去 i，得到过去几个小时的小时数
    const formattedHour = hour < 10 ? `0${hour}` : `${hour}`; // 格式化小时部分，确保两位数
    const xAxisItem = `${now.toLocaleDateString()} ${formattedHour}:00`; // 组合日期和小时部分
    xAxisData.push(xAxisItem);
  }

  return xAxisData;
},

  },
  //调用
  mounted() {
    this.$nextTick(function () {
      this.xAxisData=this.generateXAxisData();
      this.drawLine("main");
    });
  },
};
</script>