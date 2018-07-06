/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AkcioniPlanTestModule } from '../../../test.module';
import { ProjekatBodovanjeComponent } from 'app/entities/projekat-bodovanje/projekat-bodovanje.component';
import { ProjekatBodovanjeService } from 'app/entities/projekat-bodovanje/projekat-bodovanje.service';
import { ProjekatBodovanje } from 'app/shared/model/projekat-bodovanje.model';

describe('Component Tests', () => {
    describe('ProjekatBodovanje Management Component', () => {
        let comp: ProjekatBodovanjeComponent;
        let fixture: ComponentFixture<ProjekatBodovanjeComponent>;
        let service: ProjekatBodovanjeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AkcioniPlanTestModule],
                declarations: [ProjekatBodovanjeComponent],
                providers: []
            })
                .overrideTemplate(ProjekatBodovanjeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProjekatBodovanjeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjekatBodovanjeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ProjekatBodovanje(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.projekatBodovanjes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
