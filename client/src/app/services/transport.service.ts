import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class TransportService {
  constructor(private http: HttpClient) {}

  getAll() {
    return this.http.get('/api/v1/company/transport');
  }

  getAllByBatchId(batchid: any) {
    return this.http.get(`/api/v1/company/transport/${batchid}`);
  }

  createOne(data: any) {
    return this.http.post('/api/v1/company/transport', data);
  }
}
