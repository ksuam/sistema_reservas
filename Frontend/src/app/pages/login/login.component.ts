import { Component, signal, ViewEncapsulation } from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ToastModule } from 'primeng/toast';
import { CardModule } from 'primeng/card';
import { MessageService } from 'primeng/api';
import { CommonModule } from '@angular/common';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { FloatLabelModule } from 'primeng/floatlabel';
import { AuthService } from '../../services/auth.service';
import { SharedDataServiceService } from '../../services/shared-data-service.service';
import { Router } from '@angular/router';
const PRIMENG = [
  CardModule,
  ToastModule,
  InputTextModule,
  ButtonModule,
  FloatLabelModule,
];
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, FormsModule, PRIMENG, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  providers: [MessageService],
  encapsulation: ViewEncapsulation.None,
})
export class LoginComponent {
  constructor(
    private messageService: MessageService,
    private _authService: AuthService,
    private _sharedDataService: SharedDataServiceService,
    private router:Router
  ) {}

  form = signal<FormGroup>(
    new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    })
  );
ngOnInit(){
  this.form().reset()
}
  auth() {
    if (this.form().valid) {
      this._authService.auth(this.form().value).subscribe({
        next: (response) => {
          //(response);
          this._sharedDataService.setUserData(response);
          setTimeout(()=>{this.router.navigate(['/home']),300})


        },
        error: (error) => {
          //(error.error.code);
          //(error.error.message);
          this.showError(error.error.message); // usa showError en lugar de showAdd
        },
      });
    } else {
      this.showError('Formulario Invalido')
    }
  }
  showError(message:string) {
    this.messageService.add({
      severity: 'error',
      summary: 'Error',
      detail: message,
    });
  }
  showAdd() {
    this.messageService.add({
      severity: 'success',
      summary: 'Actualizado',
      detail: 'La licitación ha sido actualizada con éxito'
      });
  }


}
