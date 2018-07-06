/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AkcioniPlanTestModule } from '../../../test.module';
import { ProjekatComponent } from 'app/entities/projekat/projekat.component';
import { ProjekatService } from 'app/entities/projekat/projekat.service';
import { Projekat } from 'app/shared/model/projekat.model';

describe('Component Tests', () => {
    describe('Projekat Management Component', () => {
        let comp: ProjekatComponent;
        let fixture: ComponentFixture<ProjekatComponent>;
        let service: ProjekatService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AkcioniPlanTestModule],
                declarations: [ProjekatComponent],
                providers: []
            })
                .overrideTemplate(ProjekatComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProjekatComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjekatService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Projekat(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.projekats[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
