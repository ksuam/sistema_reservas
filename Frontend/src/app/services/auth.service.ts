import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { Auth } from '../models/Auth';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  appUrl:string = environment.appUrl
  api:string = environment.authApi
  constructor(private http: HttpClient) { }

  auth(user:Auth):Observable<Auth>{
    return this.http.post<Auth>(this.appUrl+this.api,user)

  }
}
