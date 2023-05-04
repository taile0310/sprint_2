import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-pay',
  templateUrl: './pay.component.html',
  styleUrls: ['./pay.component.css']
})
export class PayComponent implements OnInit {
  totalPrice = 0;

  constructor(private shareDataService: ShareDataService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.shareDataService.getTotalPrice().subscribe(totalProduct => {
      this.totalPrice = +(totalProduct / 23000).toFixed(2);
      console.log(this.totalPrice);
      render({
        id: '#myPaypalButtons',
        currency: 'USD',
        value: this.totalPrice.toString(),
        onApprove: (async details => {
          await Swal.fire({
            text: 'Thanh toán thành công.',
            icon: 'success',
            showConfirmButton: false,
            timer: 1500
          });
          await this.router.navigateByUrl('/product/list');
        })
      });
    });

  }

}
