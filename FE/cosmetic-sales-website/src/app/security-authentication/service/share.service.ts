import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, Subject} from 'rxjs';
import {TokenStorageService} from './token-storage.service';
import {OrderDetailService} from '../../service/order-detail.service';
import {Router} from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class ShareService {
   countKey: 'count';


  constructor(private token: TokenStorageService,
              private ordersService: OrderDetailService,
              private router: Router) {
  }

  private subject = new Subject<any>();

  itemCount: BehaviorSubject<any> = new BehaviorSubject<number>(0);

  sendClickEvent() {
    this.subject.next();
  }

  getClickEvent(): Observable<any> {
    return this.subject.asObservable();
  }

  getCount(): number {
    const count = localStorage.getItem(this.countKey);
    return count ? parseInt(count, 10) : 0;
  }

  setCount(count: number): void {
    localStorage.setItem(this.countKey, count.toString());
  }

}
