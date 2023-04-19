import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {BodyComponent} from './body/body.component';
import {ShopComponent} from './shop/shop.component';


const routes: Routes = [
  {
    path: '', component: BodyComponent
  },
  {
    path: 'shop', component: ShopComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
