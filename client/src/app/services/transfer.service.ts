import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class TransferService {
  constructor(private http: HttpClient) {}

  getAll() {
    return this.http.get('/api/v1/company/transfer');
  }

  getAllByBatchId(batchid: any) {
    return this.http.get(`/api/v1/company/transfer/${batchid}`);
  }


  acceptOne(txid: any) {
    return this.http.get(`/api/v1/company/transfer/${txid}/accept`);
  }

  abortOne(txid: any) {
    return this.http.get(`/api/v1/company/transfer/${txid}/abort`);
  }

  rejectOne(txid: any) {
    return this.http.get(`/api/v1/company/transfer/${txid}/reject`);
  }

  createOne(data: any) {
    return this.http.post('/api/v1/company/transfer', data);
  }
}
