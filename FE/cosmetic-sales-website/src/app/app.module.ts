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
import {HttpClientModule} from '@angular/common/http';
import {LoginComponent} from './security-authentication/login/login.component';

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
    ForgotPasswordComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
