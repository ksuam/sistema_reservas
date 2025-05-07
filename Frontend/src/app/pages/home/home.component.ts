import { Component } from '@angular/core';
import { ReservasComponent } from "../reservas/reservas.component";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [ReservasComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
