/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AkcioniPlanTestModule } from '../../../test.module';
import { AdminKriterijumBodovanjeUpdateComponent } from 'app/entities/admin-kriterijum-bodovanje/admin-kriterijum-bodovanje-update.component';
import { AdminKriterijumBodovanjeService } from 'app/entities/admin-kriterijum-bodovanje/admin-kriterijum-bodovanje.service';
import { AdminKriterijumBodovanje } from 'app/shared/model/admin-kriterijum-bodovanje.model';

describe('Component Tests', () => {
    describe('AdminKriterijumBodovanje Management Update Component', () => {
        let comp: AdminKriterijumBodovanjeUpdateComponent;
        let fixture: ComponentFixture<AdminKriterijumBodovanjeUpdateComponent>;
        let service: AdminKriterijumBodovanjeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AkcioniPlanTestModule],
                declarations: [AdminKriterijumBodovanjeUpdateComponent]
            })
                .overrideTemplate(AdminKriterijumBodovanjeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AdminKriterijumBodovanjeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdminKriterijumBodovanjeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AdminKriterijumBodovanje(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.adminKriterijumBodovanje = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AdminKriterijumBodovanje();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.adminKriterijumBodovanje = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
