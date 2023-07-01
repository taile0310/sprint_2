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
  orderDetails: OrderDetail;
  orderDetailId: number;
  quantity: number;
  isLoggedIn = false;
  role: string;
  nameEmployee: string;
  username: string;
  currentUser: string;

  constructor(private productService: ProductService,
              private orderDetailService: OrderDetailService,
              private activatedRoute: ActivatedRoute,
              private tokenStorageService: TokenStorageService,
              private router: Router,
              private token: TokenStorageService,
              private shareService: ShareService) {
  }

  ngOnInit(): void {
    this.getDetailProduct();
    this.view();
    this.getUserId();
    this.loadHeader();
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

  addToCart2(productId: number, quantity: number) {

    if (this.token.getToken()) {
      this.userId = this.token.getUser().id;
      this.orderDetailService.changeQuantity(this.orderDetailId, quantity).subscribe(() => {
        this.orderDetailService.showAllOrder(this.userId).subscribe(data => {
          this.orderDetail = data;
        });
      });
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
        this.orderDetailService.addToCart2(this.userId, productId, quantity).subscribe(data => {
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
    } else {
      Swal.fire({
        text: 'Số lượng không được nhỏ hơn 1 .',
        icon: 'warning',
        iconColor: '#ecb49b',
        confirmButtonText: 'OK',
        confirmButtonColor: '#ecb49b',
        timer: 1500
      });
    }
  }

  loadHeader(): void {
    if (this.tokenStorageService.getToken()) {
      this.currentUser = this.tokenStorageService.getUser().username;
      this.role = this.tokenStorageService.getUser().roles[0];
      this.username = this.tokenStorageService.getUser().username;
    }
    this.isLoggedIn = this.username != null;
    this.getUsernameAccount();
  }

  getUsernameAccount() {
    if (this.tokenStorageService.getToken()) {
      this.nameEmployee = this.tokenStorageService.getUser().name;
    }
  }

}
