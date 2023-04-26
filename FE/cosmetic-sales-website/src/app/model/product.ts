import {Category} from './category';

export interface Product {
  id?: number;
  productName?: string;
  productDescription?: string;
  price?: number;
  image?: string;
  quantity?: number;
  category?: Category;
}
