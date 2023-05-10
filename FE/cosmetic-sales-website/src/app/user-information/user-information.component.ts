import { Component, OnInit } from '@angular/core';
import {OrderDetailService} from '../service/order-detail.service';
import {ProductService} from '../service/product.service';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../service/user.service';
import {TokenStorageService} from '../security-authentication/service/token-storage.service';
import {User} from '../model/user';

@Component({
  selector: 'app-user-information',
  templateUrl: './user-information.component.html',
  styleUrls: ['./user-information.component.css']
})
export class UserInformationComponent implements OnInit {
   userId: any;
   users: User;

  constructor(private productService: ProductService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private orderDetailService: OrderDetailService,
              private userService: UserService,
              private token: TokenStorageService) { }

  ngOnInit(): void {
    this.view();
    this.getUserId();
  }
  getUserId() {
    if (this.token.getToken()) {
      this.userId = this.token.getUser().id;
      this.orderDetailService.getDetailUser(this.userId).subscribe(data => {
        this.users = data;
      });
    } else {
      this.router.navigateByUrl('/login');
    }
  }

  view(): void {
    const element = document.getElementById('info');
    if (element) {
      element.scrollIntoView();
    }
  }
}
