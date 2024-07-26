import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DocumentService {

  constructor(private httpClient: HttpClient) { }

  getAllDocuments() {
    return this.httpClient.get(`/api/v1/company/doc`)

  }

  dowloadOne(id: any) {
    return this.httpClient.get(`/api/v1/company/doc/${id}`, { observe: 'response', responseType: 'blob' })

  }

  getAllAnalisys() {
    return this.httpClient.get(`/api/v1/company/doc/analisy`)
  }

  getAllCertificates() {
    return this.httpClient.get(`/api/v1/company/doc/certificate`)
  }


  uploadDocument(data: any) {
    return this.httpClient.post(`/api/v1/company/doc/upload`, data)
  }

  notarizeDocument(id: any) {
    return this.httpClient.get(`/api/v1/company/doc/notarize/${id}`)
  }


}
