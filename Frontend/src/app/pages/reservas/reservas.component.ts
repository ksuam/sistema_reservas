import {
  ChangeDetectorRef,
  Component,
  HostListener,
  signal,
  ViewEncapsulation,
} from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { FileUploadModule } from 'primeng/fileupload';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { RadioButtonModule } from 'primeng/radiobutton';
import { RatingModule } from 'primeng/rating';
import { RippleModule } from 'primeng/ripple';
import { ColumnFilter, TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { FloatLabelModule } from 'primeng/floatlabel';
import { CommonModule, DatePipe } from '@angular/common';
import { Reservation } from '../../models/Reservation';
import { Space } from '../../models/Space';
import { ReservationService } from '../../services/reservation.service';
import { SpaceService } from '../../services/space.service';
import { CalendarModule } from 'primeng/calendar';
import { User } from '../../models/User';

const PRIMENG = [
  TableModule,
  DialogModule,
  RippleModule,
  ButtonModule,
  ToastModule,
  ToolbarModule,
  ConfirmDialogModule,
  InputTextModule,
  InputTextareaModule,
  FileUploadModule,
  DropdownModule,
  TagModule,
  RadioButtonModule,
  RatingModule,
  InputTextModule,
  FormsModule,
  InputNumberModule,
  FormsModule,
  ReactiveFormsModule,
  FloatLabelModule,
  CalendarModule,
];
@Component({
  selector: 'app-reservas',
  standalone: true,

  imports: [CommonModule, PRIMENG],
  templateUrl: './reservas.component.html',
  styleUrl: './reservas.component.css',
  providers: [MessageService, ConfirmationService, DatePipe],
  encapsulation: ViewEncapsulation.None,
})
export class ReservasComponent {
  reservationDialog: boolean = false;
  user!:User
  reservation!: Reservation;

  selectedreservations!: Reservation[] | null;

  submitted: boolean = false;
  isMobileView: boolean = false;
  statuses!: any[];
  reservations: Reservation[] = [];
  reservationsCopy: Reservation[] = [];
  spaces: any[] = [];
  espacios: Space[] = [];
  identificationTypes: any[] = [];
  busqueda: string = '';
  userId!:number
  currentPage: number = 0;
  constructor(
    private _reservationService: ReservationService,
    private _spaceService: SpaceService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private cdr: ChangeDetectorRef,
    private datePipe: DatePipe
  ) {
    this.user = JSON.parse(sessionStorage.getItem('user'))
  }

  form = signal<FormGroup>(
    new FormGroup({
      userId: new FormControl('', Validators.required),
      spaceId: new FormControl(Validators.required),
      startTime: new FormControl(Validators.required),
      endTime: new FormControl('', Validators.required),
    }, { validators: this.dateRangeValidator() })
  );

  ngOnInit() {
    this.userId = this.user.id
    //(this.user)

    this.detectScreenSize();
    this.getSpaces();
    this.getreservations();
  }

  getSpaces() {
    this._spaceService.getSpaces().subscribe({
      next: (spaces) => {
        this.spaces = spaces;
        //////(spaces)
      },
      error(err) {
        //////("no spaces")
      },
    });
  }
  getreservations() {
    if(this.userId !==1){
      //("es 1")
      this._reservationService.getReservationsByUserId(this.userId).subscribe({
        next: (reservations: Reservation[]) => {
          this.reservations = reservations.map(res => ({
            ...res,
            startTime: new Date(res.startTime),
            endTime: new Date(res.endTime),
          }));
        },
      });
    }else{
      //("es diferente de 1")
      this._reservationService.getReservations().subscribe({
        next: (reservations: Reservation[]) => {
          this.reservations = reservations;
          this.reservationsCopy = reservations;
        },
      });
    }
  }
  getReservationById(reservationId: number) {
    //("dentro de getReservationById"+reservationId)
    this._reservationService.getReservationById(reservationId).subscribe({
      next: (reservation) => {
      //("respuesta getReservationById: " + JSON.stringify(reservation))
        this.reservation = reservation;
        //("reservationId dentro de getReservationById"+reservation.id);
      },
      error: (error) => {
        //(error.error.message);
      },
    });
  }

  openNew() {
    this.reservation = {};
    this.submitted = false;
    this.reservationDialog = true;
    this.form().reset();

    this.form().patchValue({
      userId: this.userId,

    });
  }

  editReservation(reservation: Reservation) {
    //("Reservacion dentro de editReservation"+JSON.stringify(reservation))
    //("reservationId dentro de editReservation"+reservation.id);

    this.reservationDialog = true;
    this.getReservationById(reservation.id);

    this.form().patchValue({
      userId: reservation.user.id,
      spaceId: reservation.space?.id,
      startTime: new Date(reservation.startTime),
      endTime: new Date(reservation.endTime),
    });
  }

  deleteReservation(reservation: Reservation) {
    //////("Documento eliminar"+reservation.document)
    this.confirmationService.confirm({
      message: 'Desea eliminar el usuario  ?',
      header: 'Confirmar',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this._reservationService.deleteReservation(reservation.id!).subscribe({
          next: () => {
            this.reservations = this.reservations.filter(
              (val) => val.id !== reservation.id
            );
            ////////('Usuario eliminado');
            this.messageService.add({
              severity: 'success',
              summary: 'Eliminado',
              detail: 'Usuario Eliminado',
            });
          },
          error: (error) => {
            ////////(error);
            this.showErrorDelete();
          },
        });
      },
    });
  }

  hideDialog() {
    this.reservationDialog = false;
    this.submitted = false;
    this.form().reset();
  }

  saveReservation() {


    if (this.reservation.id) {
      //("dentro de primer if")
      const index = this.findIndexById(this.reservation.id!);
      //("debajo dde findIndexByID")
      if (index !== -1) {
        const formValue = this.form().value;

        const formattedData = {
          id: this.reservation.id,
          ...formValue,
          startTime: this.datePipe.transform(
            formValue.startTime,
            "yyyy-MM-dd'T'HH:mm:ss"
          ),
          endTime: this.datePipe.transform(
            formValue.endTime,
            "yyyy-MM-dd'T'HH:mm:ss"
          ),
        }
        this._reservationService
          .updateReservation(formattedData)
          .subscribe({
            next:()=>{
              this.getreservations();
            this.showSuccessUpdate();
            this.form().reset();
            this.hideDialog();
            },error:(err)=> {
              this.showError(err.error.message)
            },
          });
      } else {
        console.error('reservation not found');
      }
    } else {
      ////////('crear usuario');
      // Crear un nuevo reservationo
      this._reservationService.createReservation(this.form().value).subscribe({
        next: (data) => {
          //("Cerar reserva "+ JSON.stringify(this.form().value));
          this.getreservations();
          this.showSuccessCreate();
          ////////('Usuario creado');
          this.form().reset();
          this.hideDialog();
        },
        error: (error) => {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'Error al crear Usuario ' + this.form().value.nombre,
          });
          ////////(error);
        },
      });
    }
  }

  dateRangeValidator(): ValidatorFn {
    return (group: AbstractControl): ValidationErrors | null => {
      const start = group.get('startTime')?.value;
      const end = group.get('endTime')?.value;

      if (!start || !end) return null;

      const startDate = new Date(start);
      const endDate = new Date(end);

      const diffInMs = endDate.getTime() - startDate.getTime();
      const diffInDays = diffInMs / (1000 * 60 * 60 * 24);

      if (diffInDays < 1 || diffInDays > 2) {
        return { dateRangeInvalid: true };
      }

      return null;
    };
  }
  @HostListener('window:resize', [])
  detectScreenSize(): void {
    this.isMobileView = window.innerWidth <= 768;
  }

  findIndexById(reservationId: number): number {
    let index = -1;
    for (let i = 0; i < this.reservations.length; i++) {
      if (this.reservations[i].id === reservationId) {
        index = i;
        break;
      }
    }
    return index;
  }
  showSuccessCreate() {
    this.messageService.add({
      severity: 'success',
      summary: 'Creado',
      detail: 'Usuario Creado',
    });
  }
  showSuccessUpdate() {
    this.messageService.add({
      severity: 'success',
      summary: 'Actualizado',
      detail: 'Reservacion Actualizada',
    });
  }
  showErrorDelete() {
    this.messageService.add({
      severity: 'error',
      summary: 'Error',
      detail: 'Error al eliminar',
    });
  }

  showError(message:string) {
    this.messageService.add({
      severity: 'error',
      summary: 'Error',
      detail: message,
    });
  }
}
