/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AkcioniPlanTestModule } from '../../../test.module';
import { KriterijumUpdateComponent } from 'app/entities/kriterijum/kriterijum-update.component';
import { KriterijumService } from 'app/entities/kriterijum/kriterijum.service';
import { Kriterijum } from 'app/shared/model/kriterijum.model';

describe('Component Tests', () => {
    describe('Kriterijum Management Update Component', () => {
        let comp: KriterijumUpdateComponent;
        let fixture: ComponentFixture<KriterijumUpdateComponent>;
        let service: KriterijumService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AkcioniPlanTestModule],
                declarations: [KriterijumUpdateComponent]
            })
                .overrideTemplate(KriterijumUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(KriterijumUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KriterijumService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Kriterijum(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.kriterijum = entity;
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
                    const entity = new Kriterijum();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.kriterijum = entity;
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
