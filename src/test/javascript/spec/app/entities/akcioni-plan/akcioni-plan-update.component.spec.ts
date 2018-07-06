/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AkcioniPlanTestModule } from '../../../test.module';
import { AkcioniPlanUpdateComponent } from 'app/entities/akcioni-plan/akcioni-plan-update.component';
import { AkcioniPlanService } from 'app/entities/akcioni-plan/akcioni-plan.service';
import { AkcioniPlan } from 'app/shared/model/akcioni-plan.model';

describe('Component Tests', () => {
    describe('AkcioniPlan Management Update Component', () => {
        let comp: AkcioniPlanUpdateComponent;
        let fixture: ComponentFixture<AkcioniPlanUpdateComponent>;
        let service: AkcioniPlanService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AkcioniPlanTestModule],
                declarations: [AkcioniPlanUpdateComponent]
            })
                .overrideTemplate(AkcioniPlanUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AkcioniPlanUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AkcioniPlanService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AkcioniPlan(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.akcioniPlan = entity;
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
                    const entity = new AkcioniPlan();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.akcioniPlan = entity;
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
