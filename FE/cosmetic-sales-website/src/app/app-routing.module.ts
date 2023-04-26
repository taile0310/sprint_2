import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {BodyComponent} from './body/body.component';
import {ShopComponent} from './shop/shop.component';
import {LoginComponent} from './security-authentication/login/login.component';
import {SignUpComponent} from './security-authentication/sign-up/sign-up.component';
import {DetailShopComponent} from './detail-shop/detail-shop.component';
import {ListUserComponent} from './user/list-user/list-user.component';
import {UserGuard} from './security-authentication/security-auth/user.guard';
import {AdminGuard} from './security-authentication/security-auth/admin.guard';
import {ErrorComponent} from './error/error.component';


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
  }, {
    path: '**', component: ErrorComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
