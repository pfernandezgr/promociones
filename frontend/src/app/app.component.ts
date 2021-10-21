import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {KeycloakService} from 'keycloak-angular';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  public promocion: any = {};
  // @ts-ignore
  public ofertas: any[];

  constructor(private http: HttpClient, private keycloakService: KeycloakService) {
    this.loadPromociones();
  }

  enviar(): void {
    if (this.validate()) {
      this.http.post('/api/promociones', this.promocion).subscribe(
        () => {
          this.loadPromociones();
        },
        (error) => {
          alert(error.error);
        }
      );
    }
  }

  loadPromociones(): void {
    this.http.get('/api/promociones').subscribe(ofertas => {
      // @ts-ignore
      this.ofertas = ofertas;
    });
  }

  validate(): boolean {
    const errores: string[] = [];
    if (this.promocion.titulo) {
      if (this.promocion.titulo.length < 5) {
        errores.push('El título tiene que ser mayor que 5');
      }
      if (this.promocion.titulo.length > 45) {
        errores.push('El título tiene que ser menor que 45');
      }
    } else {
      errores.push('Debe ingresar el titulo');
    }

    if (this.promocion.descripcion) {
      if (this.promocion.descripcion.length > 255) {
        errores.push('La descripcion tiene que ser menor que 255');
      }
    } else {
      errores.push('Debe ingresar el descripcion');
    }

    if (this.promocion.precioOriginal) {
      if (!this.promocion.precioOriginal.match('[0-9]+(\\.[0-9]{1,2})?$')) {
        errores.push('El precio orignal debe tener hasta dos decimales');
      }
    } else {
      errores.push('Debe ingresar el precio original');
    }

    if (this.promocion.porcentajeDescuento) {
      if (!this.promocion.porcentajeDescuento.match('^\\d+$') || Number(this.promocion.porcentajeDescuento) > 100 || Number(this.promocion.porcentajeDescuento) < 0) {
        errores.push('El porcentaje descuento debe ser un entero entre 0 y 100');
      }
    } else {
      errores.push('Debe ingresar el procentaje descuento');
    }

    if (this.promocion.detalle) {
      if (this.promocion.detalle.length > 255) {
        errores.push('El detalle tiene que ser menor que 255');
      }
    } else {
      errores.push('Debe ingresar el detalle');
    }

    if (this.promocion.fecha) {
      if (new Date(this.promocion.fecha) < new Date()) {
        errores.push('La fecha tiene que ser mayor a hoy');
      }
    } else {
      errores.push('Debe ingresar la fecha');
    }

    if (this.promocion.cantidadDias) {
      if (!this.promocion.cantidadDias.match('^\\d+$') || Number(this.promocion.cantidadDias) > 999 || Number(this.promocion.cantidadDias) < 0) {
        errores.push('La cantidad de dias debe ser un entero de 3 digitos');
      }
    } else {
      errores.push('Debe ingresar la cantidad de dias');
    }

    if (errores.length === 0) {
      return true;
    } else {
      alert(errores.join(','));
      return false;
    }
  }

  logOut(): void {
    this.keycloakService.logout();
  }
}
