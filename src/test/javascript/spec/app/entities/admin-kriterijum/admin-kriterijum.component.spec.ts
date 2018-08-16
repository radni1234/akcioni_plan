/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AkcioniPlanTestModule } from '../../../test.module';
import { AdminKriterijumComponent } from 'app/entities/admin-kriterijum/admin-kriterijum.component';
import { AdminKriterijumService } from 'app/entities/admin-kriterijum/admin-kriterijum.service';
import { AdminKriterijum } from 'app/shared/model/admin-kriterijum.model';

describe('Component Tests', () => {
    describe('AdminKriterijum Management Component', () => {
        let comp: AdminKriterijumComponent;
        let fixture: ComponentFixture<AdminKriterijumComponent>;
        let service: AdminKriterijumService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AkcioniPlanTestModule],
                declarations: [AdminKriterijumComponent],
                providers: []
            })
                .overrideTemplate(AdminKriterijumComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AdminKriterijumComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdminKriterijumService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AdminKriterijum(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.adminKriterijums[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
