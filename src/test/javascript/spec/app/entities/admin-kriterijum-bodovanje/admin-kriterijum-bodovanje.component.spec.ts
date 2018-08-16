/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AkcioniPlanTestModule } from '../../../test.module';
import { AdminKriterijumBodovanjeComponent } from 'app/entities/admin-kriterijum-bodovanje/admin-kriterijum-bodovanje.component';
import { AdminKriterijumBodovanjeService } from 'app/entities/admin-kriterijum-bodovanje/admin-kriterijum-bodovanje.service';
import { AdminKriterijumBodovanje } from 'app/shared/model/admin-kriterijum-bodovanje.model';

describe('Component Tests', () => {
    describe('AdminKriterijumBodovanje Management Component', () => {
        let comp: AdminKriterijumBodovanjeComponent;
        let fixture: ComponentFixture<AdminKriterijumBodovanjeComponent>;
        let service: AdminKriterijumBodovanjeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AkcioniPlanTestModule],
                declarations: [AdminKriterijumBodovanjeComponent],
                providers: []
            })
                .overrideTemplate(AdminKriterijumBodovanjeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AdminKriterijumBodovanjeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdminKriterijumBodovanjeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AdminKriterijumBodovanje(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.adminKriterijumBodovanjes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
