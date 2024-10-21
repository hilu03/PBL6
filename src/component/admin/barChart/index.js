import * as React from 'react';
import "./style.scss"
import { BarChart } from '@mui/x-charts/BarChart';
import { axisClasses } from '@mui/x-charts/ChartsAxis';
import { useEffect } from 'react';

const dataset = [
  { category: 'Văn học việt nam', sales: 2000 },
  { category: 'Văn học nước ngoài', sales: 1000 },
  { category: 'Kiến thức - khoa học', sales: 1800 },
  { category: 'Lịch sử - truyền thống', sales: 1600 },
  { category: 'Sách công cụ Đoàn - Đội', sales: 800 },
];

const valueFormatter = (value) => `${value} lượt`;

const chartSetting = {
  yAxis: [
    {
      label: 'Lượt bán',
      sx: {
        // Điều chỉnh vị trí nhãn ở đây nếu thư viện hỗ trợ
        transform: 'rotate(-90, -28, 120)', // Thay đổi giá trị này để đẩy lên hoặc xuống
      },
    },
  ],
  series: [
    { dataKey: 'sales', 
      label: 'Số lượng bán',
      valueFormatter ,
      color: '#9d2ff7',
    }, 
  ],
  height: 300,
  sx: {
    [`& .${axisClasses.directionY} .${axisClasses.label}`]: {
      transform: 'translateX(-10px)',
    },
  },
};

export default function Top5CategoriesChart() {
  const [tickPlacement, setTickPlacement] = React.useState('middle');
  const [tickLabelPlacement, setTickLabelPlacement] = React.useState('middle');

  return (
    <div style={{ width: '100%' }}>
      <BarChart
        margin={{
          left: 80,
        }}
        dataset={dataset}    
        xAxis={[{ scaleType: 'band', dataKey: 'category', tickPlacement, tickLabelPlacement }]} 
        {...chartSetting}
      />
    </div>
  );
}
