import {Component, OnInit} from '@angular/core';
import {UserService} from '../../service/user.service';
import {User} from '../../model/user';
import {Product} from '../../model/product';

@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.css']
})
export class ListUserComponent implements OnInit {
  users: User[];
  user: any;
  currentPage = 0;
  size = 5;
  totalPages: number;
  page = 0;

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    this.getAll();
  }

  getAll() {
    this.userService.getListUser(this.currentPage, this.size).subscribe(data => {
      this.user = data;
      this.users = this.user.content;
      this.totalPages = this.user.totalPages;
    });
  }

  previous() {
    if (this.page > 0) {
      this.page--;
      this.getAll();
    }
  }

  next() {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.getAll();
    }
  }
}
