import {User} from './user';
import {Product} from './product';

export interface Orders {
  id?: number;
  user?: User;
  orderDate?: string;
  totalAmount?: number;
  note?: string;
  quantity?: number;
  status?: number;
  product?: Product;
}
