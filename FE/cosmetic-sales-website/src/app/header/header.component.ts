import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from '../security-authentication/service/token-storage.service';
import {ShareService} from '../security-authentication/service/share.service';
import {Router} from '@angular/router';
import {OrderDetailService} from '../service/order-detail.service';
import Swal from 'sweetalert2';
import {ProductService} from '../service/product.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  username: string;
  currentUser: string;
  nameEmployee: string;
  role: string;
  isLoggedIn = false;
  constructor(private tokenStorageService: TokenStorageService,
              private shareService: ShareService,
              private ordersService: OrderDetailService,
              private token: TokenStorageService,
              private router: Router,
              private productService: ProductService) {
  }

  get counts(): number {
    return this.shareService.getCount();
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

  ngOnInit(): void {
    this.shareService.getClickEvent().subscribe(() => {
      this.loadHeader();
    });
    this.loadHeader();
  }

  async logOut() {
    this.tokenStorageService.signOut();
    this.shareService.sendClickEvent();
    await Swal.fire({
      text: 'Đăng xuất thành công',
      icon: 'success',
      iconColor: '#ecb49b',
      showConfirmButton: false,
      timer: 1500
    });
    await this.router.navigateByUrl('/');
    location.reload();
  }

  getUsernameAccount() {
    if (this.tokenStorageService.getToken()) {
      this.nameEmployee = this.tokenStorageService.getUser().name;
    }
  }
}
