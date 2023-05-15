import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FooterComponent } from './footer/footer.component';
import { BodyComponent } from './body/body.component';
import { HeaderComponent } from './header/header.component';
import { ShopComponent } from './shop/shop.component';
import {ChangePasswordComponent} from './security-authentication/change-password/change-password.component';
import {SignUpComponent} from './security-authentication/sign-up/sign-up.component';
import {DetailShopComponent} from './detail-shop/detail-shop.component';
import {ForgotPasswordComponent} from './security-authentication/forgot-password/forgot-password.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {LoginComponent} from './security-authentication/login/login.component';
import {AuthInterceptor} from './security-authentication/security-auth/auth.interceptor';
import { ListUserComponent } from './user/list-user/list-user.component';
import { ErrorComponent } from './error/error.component';
import {CardComponent} from './card/card.component';
import {PayComponent} from './pay/pay.component';
import { PaymentHistoryComponent } from './payment-history/payment-history.component';
import { UserInformationComponent } from './user-information/user-information.component';
import { ListCustomerComponent } from './user/list-customer/list-customer.component';
import { ContactComponent } from './contact/contact.component';

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    BodyComponent,
    HeaderComponent,
    ShopComponent,
    LoginComponent,
    SignUpComponent,
    DetailShopComponent,
    ChangePasswordComponent,
    ForgotPasswordComponent,
    ListUserComponent,
    ErrorComponent,
    CardComponent,
    PayComponent,
    PaymentHistoryComponent,
    UserInformationComponent,
    ListCustomerComponent,
    ContactComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
