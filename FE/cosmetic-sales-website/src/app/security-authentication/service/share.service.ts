import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {TokenStorageService} from './token-storage.service';
import {OrderDetailService} from '../../service/order-detail.service';
import {Router} from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class ShareService {


  constructor(private token: TokenStorageService,
              private ordersService: OrderDetailService,
              private router: Router) {
  }

  private subject = new Subject<any>();

  sendClickEvent() {
    this.subject.next();
  }

  getClickEvent(): Observable<any> {
    return this.subject.asObservable();
  }

}
