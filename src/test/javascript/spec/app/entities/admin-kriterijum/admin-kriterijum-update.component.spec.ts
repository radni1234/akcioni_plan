/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AkcioniPlanTestModule } from '../../../test.module';
import { AdminKriterijumUpdateComponent } from 'app/entities/admin-kriterijum/admin-kriterijum-update.component';
import { AdminKriterijumService } from 'app/entities/admin-kriterijum/admin-kriterijum.service';
import { AdminKriterijum } from 'app/shared/model/admin-kriterijum.model';

describe('Component Tests', () => {
    describe('AdminKriterijum Management Update Component', () => {
        let comp: AdminKriterijumUpdateComponent;
        let fixture: ComponentFixture<AdminKriterijumUpdateComponent>;
        let service: AdminKriterijumService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AkcioniPlanTestModule],
                declarations: [AdminKriterijumUpdateComponent]
            })
                .overrideTemplate(AdminKriterijumUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AdminKriterijumUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdminKriterijumService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AdminKriterijum(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.adminKriterijum = entity;
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
                    const entity = new AdminKriterijum();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.adminKriterijum = entity;
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
