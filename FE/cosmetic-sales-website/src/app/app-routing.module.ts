import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {BodyComponent} from './body/body.component';
import {ShopComponent} from './shop/shop.component';
import {LoginComponent} from './security-authentication/login/login.component';
import {SignUpComponent} from './security-authentication/sign-up/sign-up.component';
import {DetailShopComponent} from './detail-shop/detail-shop.component';
import {ListUserComponent} from './user/list-user/list-user.component';
import {AdminGuard} from './security-authentication/security-auth/admin.guard';
import {ErrorComponent} from './error/error.component';
import {CardComponent} from './card/card.component';
import {PayComponent} from './pay/pay.component';
import {PaymentHistoryComponent} from './payment-history/payment-history.component';
import {UserInformationComponent} from './user-information/user-information.component';
import {ListCustomerComponent} from './user/list-customer/list-customer.component';


const routes: Routes = [
  {
    path: '', component: BodyComponent
  },
  {
    path: 'shop', component: ShopComponent
  },
  {
    path: 'login', component: LoginComponent
  },
  {
    path: 'sign-up', component: SignUpComponent
  },
  {
    path: 'detail/:id', component: DetailShopComponent
  },
  {
    path: 'user/list', component: ListUserComponent, canActivate: [AdminGuard]
  },
  {
    path: 'customer', component: ListCustomerComponent
  },
  {
    path: 'cart', component: CardComponent
  },
  {
    path: 'pay', component: PayComponent
  },
  {
    path: 'pay-history', component: PaymentHistoryComponent
  },
  {
    path: 'info', component: UserInformationComponent
  },
  {
    path: '**', component: ErrorComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
