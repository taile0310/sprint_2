import {Component, OnInit} from '@angular/core';
import {Chart, registerables} from 'chart.js';
import {OrderDetailService} from '../service/order-detail.service';

Chart.register(...registerables);

@Component({
  selector: 'app-statistic',
  templateUrl: './statistic.component.html',
  styleUrls: ['./statistic.component.css']
})
export class StatisticComponent implements OnInit {
  private canvas: HTMLCanvasElement;
  private chart: Chart;
  statisticalOfYear = '2023';
  totalRevenue: number;
  totalQuantityOrder: number;


  constructor(private orderDetailService: OrderDetailService) {
  }

  ngOnInit(): void {
    this.statistical();
    this.view();
  }

  statistical() {
    this.canvas = document.getElementById('myChart') as HTMLCanvasElement;
    this.canvas.width = 1000; // Thiết lập chiều rộng của canvas
    this.canvas.height = 250; // Thiết lập chiều cao của canvas
    if (this.chart) {
      this.chart.destroy();
    }
    this.chart = new Chart(this.canvas, {
      type: 'bar',
      data: {
        labels: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6',
          'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'], // Nhãn trục x
        datasets: [{
          label: 'Doanh thu theo tháng ', // Nhãn dataset
          data: [], // Dữ liệu
          backgroundColor: '#ecb49b' // Màu nền
        }]
      },
      options: {
        responsive: true,
        scales: {
          y: {
            type: 'linear', // Sử dụng loại scale "linear"
            beginAtZero: true, // Bắt đầu trục y từ 0
            ticks: {
              stepSize: 2000000,
            }
          }
        }
      }
    });
    this.orderDetailService.showAllStatistical(this.statisticalOfYear).subscribe(data => {
      const revenueData = data.map(stat => stat.totalAmount || 0);
      const quantityOrder = data.map(stat => stat.quantityOrder || 0);
      this.chart.data.datasets[0].data = revenueData;
      // tổng doanh thu của 1 năm
      this.totalRevenue = revenueData.reduce((a, b) => a + b, 0);
      // tổng đơn đặt hàng của 1 năm
      this.totalQuantityOrder = quantityOrder.reduce((a, b) => a + b, 0);
      this.chart.update();
    });

  }
  view(): void {
    const element = document.getElementById('statistical');
    if (element) {
      element.scrollIntoView();
    }
  }
}


