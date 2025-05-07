import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Reservation } from '../models/Reservation';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  appUrl:string = environment.appUrl
  api:string = environment.reservationsApi
  constructor(private http: HttpClient) { }

getReservations():Observable<Reservation[]>{
return this.http.get<Reservation[]>(this.appUrl+this.api+'/todas')
}

getReservationById(reservationId:number):Observable<Reservation>{
return this.http.get<Reservation>(this.appUrl+this.api+ '/detalle/'+reservationId)
}
getReservationsByUserId(userId:number):Observable<Reservation[]>{
return this.http.get<Reservation[]>(this.appUrl+this.api+ '/usuario/'+userId)

}

deleteReservation(reservationId:number):Observable<void>{
return this.http.delete<void>(this.appUrl+this.api+'/eliminar/'+reservationId)
}

updateReservation(reservation:Reservation):Observable<void>{
return this.http.patch<void>(this.appUrl+this.api+'/actualizar/'+reservation.id,reservation)
}

createReservation(reservation:Reservation):Observable<any>{
return this.http.post(this.appUrl+this.api+'/crear',reservation)
}
}
