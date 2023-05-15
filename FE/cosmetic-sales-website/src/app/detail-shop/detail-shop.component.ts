import {Component, OnInit} from '@angular/core';
import {ProductService} from '../service/product.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Product} from '../model/product';
import {TokenStorageService} from '../security-authentication/service/token-storage.service';
import {OrderDetailService} from '../service/order-detail.service';
import {OrderDetail} from '../model/order-detail';
import Swal from 'sweetalert2';
import {ShareService} from '../security-authentication/service/share.service';

@Component({
  selector: 'app-detail-shop',
  templateUrl: './detail-shop.component.html',
  styleUrls: ['./detail-shop.component.css']
})
export class DetailShopComponent implements OnInit {
  id: number;
  product: Product;
  userId: number;
  orderDetail: OrderDetail[] = [];

  constructor(private productService: ProductService,
              private orderDetailService: OrderDetailService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private token: TokenStorageService,
              private shareService: ShareService) {
  }

  ngOnInit(): void {
    this.getDetailProduct();
    this.view();
    this.getUserId();
  }
  getDetailProduct() {
    this.activatedRoute.paramMap.subscribe(data => {
      this.id = +data.get('id');
    });
    this.productService.getDetailProduct(this.id).subscribe(data => {
      this.product = data;
    });
  }

  view(): void {
    const element = document.getElementById('detail-shop');
    if (element) {
      element.scrollIntoView();
    }
  }

  addToCart(productId: number, quantity: number) {
    if (this.token.getToken()) {
      this.userId = this.token.getUser().id;
      // Lấy thông tin sản phẩm trong kho
      if (quantity < 1) {
        Swal.fire({
          text: 'Sản phẩm đã hết hàng.',
          icon: 'error',
          iconColor: '#ecb49b',
          confirmButtonText: 'OK',
          confirmButtonColor: '#ecb49b',
          timer: 1500
        });
      } else {
        // Thêm vào giỏ hàng nếu còn hàng
        this.orderDetailService.addToCart(this.userId, productId, quantity).subscribe(data => {
          Swal.fire({
            text: 'Sản phẩm đã được thêm vào giỏ hàng.',
            icon: 'success',
            iconColor: '#ecb49b',
            confirmButtonText: 'OK',
            confirmButtonColor: '#ecb49b',
            timer: 1500
          });
          this.orderDetailService.showAllOrder(this.userId).subscribe(next => {
            this.orderDetail = next;
            this.shareService.setCount(next.length); // lấy count tương ứng với length
          });
        });
      }
    } else {
      Swal.fire({
        text: 'Bạn cần đăng nhập trước khi thêm vào giỏ hàng.',
        icon: 'warning',
        iconColor: '#ecb49b',
        confirmButtonText: 'OK',
        confirmButtonColor: '#ecb49b',
        timer: 1500
      });
      this.router.navigateByUrl('/login');
    }
  }

  getUserId() {
    if (this.token.getToken()) {
      this.userId = this.token.getUser().id;
    } else {
      this.getDetailProduct();
    }
  }


  inc() {
    this.product.quantity++;
  }

  desc() {
    if (this.product.quantity > 1) {
      this.product.quantity--;
    }
  }

}
