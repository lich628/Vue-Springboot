import Mock from 'mockjs';

// 生成模拟温度和湿度数据
const generateRealisticData = () => {
  const data = [];
  const initialTemperature = 25; // 初始温度
  const initialHumidity = 35; // 初始湿度
  const temperatureFluctuation = 2; // 温度每小时波动范围
  const humidityFluctuation = 3; // 湿度每小时波动范围

  for (let i = 0; i < 6; i++) {
    const temperature = initialTemperature + Mock.Random.float(-temperatureFluctuation, temperatureFluctuation);
    const humidity = initialHumidity + Mock.Random.float(-humidityFluctuation, humidityFluctuation);
    
    data.push({
      temperature:temperature.toFixed(2),
      humidity:humidity.toFixed(2)
    });
  }

  return data;
};

// 导出生成的数据
export const mockData = generateRealisticData();
