import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class NotarizationService {
  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get(`/api/v1/company/notarization`);
  }
}
