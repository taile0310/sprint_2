import {Component, OnInit} from '@angular/core';
import {Product} from '../model/product';
import {ProductService} from '../service/product.service';
import {Category} from '../model/category';
import {OrderDetailService} from '../service/order-detail.service';
import {ShareService} from '../security-authentication/service/share.service';
import {TokenStorageService} from '../security-authentication/service/token-storage.service';
import {Router} from '@angular/router';
import Swal from 'sweetalert2';
import {OrderDetail} from '../model/order-detail';
import {UserService} from '../service/user.service';
import {forkJoin} from 'rxjs';
import {filter} from 'rxjs/operators';

@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.css']
})
export class ShopComponent implements OnInit {

  products: Product[] = [];
  category: Category[] = [];
  orderDetail: OrderDetail[] = [];
  product: any;
  currentPage = 0;
  size = 6;
  totalPages = 0;
  categoryId = 0;
  productName = '';
  userId: number;
  searchError: boolean;
  productDelete: Product = {};
  isLoggedIn = false;
  role: string;
  nameEmployee: string;
  username: string;
  currentUser: string;
  minPrice: number;
  maxPrice: number;
  filteredProducts: Product[];
  isFiltered: boolean;

  constructor(private userService: UserService,
              private productService: ProductService,
              private orderDetailService: OrderDetailService,
              private tokenStorageService: TokenStorageService,
              private shareService: ShareService,
              private token: TokenStorageService,
              private router: Router) {
    this.productService.getListCategory().subscribe(data => {
      this.category = data;
    });
    this.shareService.getClickEvent().subscribe(value => {
      this.getUserId();
    });
  }

  getUserId() {
    if (this.token.getToken()) {
      this.userId = this.token.getUser().id;
    } else {
      this.router.navigateByUrl('/shop');
    }
  }


  ngOnInit(): void {
    this.view();
    this.getAll();
    this.getUserId();
    this.loadHeader();

  }


  getAll() {
    this.productService.getListProduct(this.currentPage, this.size, this.productName, this.categoryId).subscribe(data => {
      this.product = data;
      if (this.product !== null) {
        this.products = this.product.content;
        this.totalPages = this.product.totalPages;
        this.searchError = false;
      } else {
        this.products = [];
        this.totalPages = 0;
        this.searchError = true;
      }
    });
  }

  loadMore() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.productService.getListProduct(this.currentPage, this.size, this.productName, this.categoryId).subscribe(data => {
        console.log(data);
        this.product = data;
        if (this.product !== null) {
          this.products = this.products.concat(this.product.content);
          this.searchError = false;
        }
      });
    }
  }

  search() {
    this.currentPage = 0;
    this.getAll();
  }

  filterProducts(event: any) {
    const value = event.target.value; // Lấy giá trị từ ô input
    const filterValues = value.split(','); // Tách các giá trị lọc thành một mảng

    // Khởi tạo mảng để lưu các kết quả đã lọc
    this.filteredProducts = [];
    this.currentPage = 0;

    // tslint:disable-next-line:no-shadowed-variable
    for (const filter of filterValues) {
      const [minPrice, maxPrice] = filter.split('-'); // Tách giá trị min và max

      // Chắc chắn rằng minPrice và maxPrice là số hợp lệ
      // tslint:disable-next-line:radix
      const parsedMinPrice = parseInt(minPrice);
      // tslint:disable-next-line:radix
      const parsedMaxPrice = parseInt(maxPrice);

      if (!isNaN(parsedMinPrice) && !isNaN(parsedMaxPrice)) {
        this.currentPage = 0;
        this.productService.filterPrice(this.currentPage, this.size, parsedMinPrice, parsedMaxPrice)
          .subscribe(data => {
            console.log(data);
            this.product = data;
            if (this.product !== null) {
              const filteredProducts = this.product.content;

              // Thêm các sản phẩm đã lọc vào mảng filteredProducts
              for (const product of filteredProducts) {
                this.filteredProducts.push(product);
              }

              this.totalPages = this.product.totalPages;
              this.searchError = false;
              this.isFiltered = true; // Đánh dấu là đã lọc danh sách sản phẩm
            } else {
              this.totalPages = 0;
              this.searchError = true;
              this.isFiltered = false; // Đánh dấu là không có kết quả sau khi lọc
            }
          });
      } else {
        // Xử lý khi giá trị minPrice hoặc maxPrice không hợp lệ
        this.filteredProducts = [];
        this.totalPages = 0;
        this.searchError = true;
        this.isFiltered = false; // Đánh dấu là không có kết quả sau khi lọc
      }

    }

  }


  clearInputs() {
    this.productName = '';
    this.categoryId = 0;
    this.search();
  }


  previous() {
    if (this.currentPage > 0) {
      this.currentPage--;
    }
    this.getAll();

  }

  next() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
    }
    this.getAll();

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

  delete(id: number) {
    if (id != null) {
      return this.productService.deleteById(this.productDelete.id).subscribe(data => {
        Swal.fire({
          text: 'Xóa sản phẩm thành công.',
          icon: 'success',
          iconColor: '#ecb49b',
          confirmButtonText: 'OK',
          confirmButtonColor: '#ecb49b',
          timer: 1500
        });
        this.getAll();
      }, error => {
        Swal.fire({
          text: 'Xóa sản phẩm không thành công.',
          icon: 'warning',
          iconColor: '#ecb49b',
          confirmButtonText: 'OK',
          confirmButtonColor: '#ecb49b',
          timer: 1500
        });
      });
    }
  }


  view(): void {
    const element = document.getElementById('shop');
    if (element) {
      element.scrollIntoView();
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
