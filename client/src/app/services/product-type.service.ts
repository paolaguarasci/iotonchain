import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ProductTypeService {
  constructor(private http: HttpClient) {}
  getAll() {
    return this.http.get('/api/v1/company/product-type');
  }

  createOne(data: any) {
    return this.http.post('/api/v1/company/product-type', data);
  }

  
}
