import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {SubjectSoftwareModel} from '../model/subjectSoftware.model';
import {CertificateModel} from '../model/certificate.model';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable()
export class SecurityService {

  private BASE_URL = 'http://localhost:8080/api/security';

  constructor(private http: HttpClient) {
  }

  getSubjectSoftware(): Observable<SubjectSoftwareModel[]> {
    return this.http.get<SubjectSoftwareModel[]>( `${this.BASE_URL}/getSubjectSoftware`, httpOptions);
  }


  addCertificate(model: CertificateModel): Observable<any> {
    const data = JSON.stringify(model);
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post( `${this.BASE_URL}/createCertificate`, data, {headers: headers});
  }
}
