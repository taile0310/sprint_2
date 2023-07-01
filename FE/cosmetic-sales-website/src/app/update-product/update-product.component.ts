import {Component, OnInit} from '@angular/core';
import {UserService} from '../service/user.service';
import {ProductService} from '../service/product.service';
import {OrderDetailService} from '../service/order-detail.service';
import {ShareService} from '../security-authentication/service/share.service';
import {TokenStorageService} from '../security-authentication/service/token-storage.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Product} from '../model/product';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-update-product',
  templateUrl: './update-product.component.html',
  styleUrls: ['./update-product.component.css']
})
export class UpdateProductComponent implements OnInit {
  product: Product;
  productGroup: FormGroup = new FormGroup({
    id: new FormControl(),
    productName: new FormControl('', [Validators.required]),
    productDescription: new FormControl('', [Validators.required]),
    price: new FormControl('', [Validators.required]),
    cost: new FormControl('', [Validators.required]),
    image: new FormControl(),
    quantity: new FormControl('', [Validators.required]),
    category: new FormControl(),
  });
  productId: number;

  constructor(private userService: UserService,
              private productService: ProductService,
              private orderDetailService: OrderDetailService,
              private shareService: ShareService,
              private token: TokenStorageService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.getDetailProduct();
    this.view();
  }

  getDetailProduct() {
    this.activatedRoute.paramMap.subscribe(data => {
      this.productId = +data.get('id');
    });
    this.productService.getDetailProductForUpdate(this.productId).subscribe(data => {
      this.product = data;
      this.patchProductData();
    });
  }

  // lấy giá trị trước khi cập nhập
  patchProductData() {
    this.productGroup.patchValue({
      id: this.product.id,
      productName: this.product.productName,
      productDescription: this.product.productDescription,
      price: this.product.price,
      cost: this.product.cost,
      image: this.product.image,
      quantity: this.product.quantity,
      category: this.product.category
    });
  }

  submitUpdate() {
    if (this.productGroup.valid) {
      this.productService.updateProduct(this.productGroup.value).subscribe(next => {
          this.router.navigateByUrl('/shop');
          Swal.fire({
            text: 'Cập nhập sản phẩm thành công.',
            icon: 'success',
            iconColor: '#ecb49b',
            confirmButtonText: 'OK',
            confirmButtonColor: '#ecb49b',
            timer: 3000
          });
        }, error => {
          console.log(error);
          Swal.fire({
            text: 'Cập nhập thất bại.',
            icon: 'warning',
            iconColor: '#ecb49b',
            confirmButtonText: 'OK',
            confirmButtonColor: '#ecb49b',
            timer: 3000
          });
        }
      );
    }
  }

  view(): void {
    const element = document.getElementById('update');
    if (element) {
      element.scrollIntoView();
    }
  }
}
