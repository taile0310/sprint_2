import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiListUser = 'http://localhost:8080/api/admin/list';

  constructor(private httpClient: HttpClient) {
  }

  getListUser(page: number, size: number): Observable<any> {
    debugger
    return this.httpClient.get<any>(this.apiListUser + '?page=' + page + '&size=' + size);
  }}
